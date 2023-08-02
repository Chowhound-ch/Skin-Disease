package edu.hfut.innovate.community.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.Query;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.common.util.CommunityTypeUtil;
import edu.hfut.innovate.common.util.ItemSize;
import edu.hfut.innovate.common.domain.vo.community.CommentVo;
import edu.hfut.innovate.common.domain.vo.community.ReplyVo;
import edu.hfut.innovate.common.domain.vo.community.UserVo;
import edu.hfut.innovate.community.dao.CommentDao;
import edu.hfut.innovate.community.entity.CommentEntity;
import edu.hfut.innovate.community.entity.UserEntity;
import edu.hfut.innovate.community.service.CommentService;
import edu.hfut.innovate.community.service.LikeRecordService;
import edu.hfut.innovate.community.service.ReplyService;
import edu.hfut.innovate.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService, ApplicationRunner {

    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeRecordService likeRecordService;
    // 自身的引用
    // 解决事务中调用自己的方法不生效的问题
    private CommentService commentService;


    @Override
    public PageUtils<CommentEntity> queryPage(Map<String, Object> params) {
        IPage<CommentEntity> page = this.page(
                new Query<CommentEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils<>(page);
    }

    @Override
    public Map<Long, List<CommentVo>> mapByTopicIds(Collection<Long> idSet,
                                                    Integer commentItemSize,
                                                    Integer replyItemSize) {
        List<CommentEntity> commentEntities = this.list(
                new LambdaQueryWrapper<CommentEntity>().in(CommentEntity::getTopicId, idSet));

        if (commentEntities.isEmpty()){
            return Collections.emptyMap();
        }

        // 获取所有的评论id
        Collection<Long> commentIds = new HashSet<>();
        // 获取所有的用户id
        Collection<Long> userIds = new HashSet<>();
        commentEntities.forEach(commentEntity -> {
            commentIds.add(commentEntity.getCommentId());
            userIds.add(commentEntity.getUserId());
        });
        Map<Long, List<ReplyVo>> replyVoMap;

        if (replyItemSize == null || replyItemSize > 0){
            replyVoMap = replyService.mapByCommentIdsWithSizeOf(commentIds, replyItemSize);
        } else {
            replyVoMap = null;
        }

        Map<Long, UserVo> userVoMap = userService.listByIds(userIds).stream()
                .map(userEntity -> BeanUtil.copyProperties(userEntity, new UserVo()))
                .collect(Collectors.toMap(UserVo::getUserId, Function.identity()));

        Map<Long, List<CommentVo>> commentVoMap = commentEntities.stream().map(commentEntity -> {
            CommentVo commentVo = BeanUtil.copyProperties(commentEntity, new CommentVo());
            commentVo.setTopicId(commentEntity.getTopicId());
            commentVo.setUser(userVoMap.get(commentEntity.getUserId()));

            // 获取评论的回复
            if (replyVoMap != null){
                List<ReplyVo> replyVos = replyVoMap.get(commentEntity.getCommentId());

                Stream<ReplyVo> replyVoStream = replyVos == null? Stream.empty() : replyVos.stream()
                        .sorted(Comparator.comparingInt(ReplyVo::getLikes).reversed());
                if (replyItemSize == null) {
                    commentVo.setReplies(replyVoStream.toList());
                } else { // replyItemSize != null 限制relyItemSize
                    commentVo.setRepliesByLikes(replyVoStream.limit(replyItemSize).toList());
                }
            }

            return commentVo;
        }).collect(Collectors.groupingBy(CommentVo::getTopicId));

        if (commentItemSize != null) {
            commentVoMap.entrySet().forEach(entry -> {
                if (entry.getValue().size() > commentItemSize) {
                    // 取like最多的size个
                    entry.setValue(entry.getValue().stream()
                            .sorted(Comparator.comparingInt(CommentVo::getLikes).reversed())
                            .limit(commentItemSize).toList()) ;
                }
            });
        }

        return commentVoMap;
    }

    @Override
    public List<CommentVo> getByTopicId(Long topicId, Integer commentItemSize, Integer replyItemSize) {
        return mapByTopicIds(Set.of(topicId), commentItemSize, replyItemSize).get(topicId);
    }

    @Override
    public List<CommentVo> getByTopicIdWithLikes(Long topicId, Long userId, Integer commentItemSize, Integer replyItemSize) {
        List<CommentVo> commentVos = getByTopicId(topicId, commentItemSize, replyItemSize);
        // 获取用户点赞的评论id
        Collection<Long> ids = CollectionUtil.getCollection(commentVos, CommentVo::getCommentId);
        Set<Long> likedDesIds = likeRecordService.setOfLikedDesIds(ids, userId, CommunityTypeUtil.COMMENT_TYPE);
        commentVos.forEach(commentVo -> commentVo.setIsLiked(likedDesIds.contains(commentVo.getCommentId()) ? 1 : 0));

        return commentVos;
    }

    @Override
    public CommentVo getCommentById(Long commentId, Long userId) {
        CommentEntity commentEntity = getById(commentId);
        if (commentEntity == null){
            return null;
        }
        CommentVo commentVo = BeanUtil.copyProperties(commentEntity, new CommentVo());
        // 设置UserVo
        UserEntity userEntity = userService.getById(commentEntity.getUserId());
        commentVo.setUser(BeanUtil.copyProperties(userEntity, new UserVo()));

        // 设置ReplyVo
        commentVo.setReplies(replyService.listByCommentIdWithLikes(commentId, userId, ItemSize.ALL));
        // 设置是否点赞
        commentVo.setIsLiked(likeRecordService.isLikedDesId(commentId, userId, CommunityTypeUtil.COMMENT_TYPE));

        return commentVo;
    }

    @Transactional
    @Override
    public void removeByIdWithReply(Long commentId) {
        this.removeById(commentId);
        replyService.removeByCommentId(commentId);
    }

    @Override
    public void removeAllByIdsWithReply(Collection<Long> commentIds) {
        commentService.removeByIds(commentIds);
        replyService.removeAllByCommentIds(commentIds);
    }

    @Override
    public void offsetCommentLikeCount(Long commentId, Integer offset) {
        update(new LambdaUpdateWrapper<>(CommentEntity.class)
                .eq(CommentEntity::getCommentId, commentId)
                .setSql("likes = likes + " + offset));
    }


    @Override
    public void run(ApplicationArguments args) {
        commentService = SpringUtil.getBean(CommentService.class);
        likeRecordService = SpringUtil.getBean(LikeRecordService.class);
    }
}