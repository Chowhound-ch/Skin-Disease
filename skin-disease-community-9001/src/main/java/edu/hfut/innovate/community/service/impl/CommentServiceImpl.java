package edu.hfut.innovate.community.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.domain.vo.community.CommentVo;
import edu.hfut.innovate.common.domain.vo.community.ReplyVo;
import edu.hfut.innovate.common.domain.vo.community.UserVo;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.common.util.CommunityTypeUtil;
import edu.hfut.innovate.community.dao.CommentMapper;
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
import java.util.stream.Collectors;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentEntity> implements CommentService, ApplicationRunner {

    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeRecordService likeRecordService;
    // 自身的引用
    // 解决事务中调用自己的方法不生效的问题
    private CommentService commentService;

    /**
     *
     * @param idSet : 话题id集合
     * @return
     */
    @Override
    public Map<Long, List<CommentVo>> mapByTopicIds(Collection<Long> idSet) {
        if (idSet == null || idSet.isEmpty()) return null;
        List<CommentEntity> commentEntities = baseMapper.listByTopicIds(idSet);

        if (commentEntities.isEmpty()) {
            return Collections.emptyMap();
        }
        Collection<Long> ids = CollectionUtil.getCollection(commentEntities, CommentEntity::getCommentId);
        Map<Long, List<ReplyVo>> replyMap = replyService.mapByCommentIdsWithSizeOf(ids);
        // 获取所有的评论id
        return commentEntities.stream()
                .map(commentEntity -> {
                    CommentVo commentVo = BeanUtil.copyProperties(commentEntity, new CommentVo());
                    commentVo.setReplies(replyMap.get(commentVo.getCommentId()));
                    return commentVo;
                })
                .collect(Collectors.groupingBy(CommentVo::getTopicId));
    }

    @Override
    public List<CommentVo> getByTopicId(Long topicId) {
        return mapByTopicIds(Set.of(topicId)).get(topicId);
    }

    @Override
    public List<Long> listIdByTopicId(Long topicId) {
        LambdaQueryWrapper<CommentEntity> wrapper = new LambdaQueryWrapper<CommentEntity>()
                .select(CommentEntity::getCommentId)
                .eq(CommentEntity::getTopicId, topicId);

        return listObjs(wrapper, (obj) -> Long.valueOf(obj.toString()));
    }

    @Override
    public List<CommentVo> getByTopicIdWithLikes(Long topicId, Long userId) {
        List<CommentVo> commentVos = getByTopicId(topicId);
        if (commentVos == null || commentVos.isEmpty()){
            return Collections.emptyList();
        }
        // 获取用户点赞的评论id
        Collection<Long> ids = CollectionUtil.getCollection(commentVos, CommentVo::getCommentId);
        Map<Long, List<ReplyVo>> replyMap = replyService.mapByCommentIdsWithSizeOf(ids);
        Set<Long> likedDesIds = likeRecordService.setOfLikedDesIds(ids, userId, CommunityTypeUtil.COMMENT_TYPE);
        commentVos.forEach(commentVo -> {
            commentVo.setIsLiked(likedDesIds.contains(commentVo.getCommentId()) ? 1 : 0);
            commentVo.setReplies(replyMap.get(commentVo.getCommentId()));
        }
        );

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
        commentVo.setReplies(replyService.listByCommentIdWithLikes(commentId, userId));
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