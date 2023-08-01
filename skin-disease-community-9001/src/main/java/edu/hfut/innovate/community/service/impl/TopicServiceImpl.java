package edu.hfut.innovate.community.service.impl;

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
import edu.hfut.innovate.common.vo.community.CommentVo;
import edu.hfut.innovate.common.vo.community.TopicVo;
import edu.hfut.innovate.common.vo.community.UserVo;
import edu.hfut.innovate.community.dao.TopicDao;
import edu.hfut.innovate.community.entity.TopicEntity;
import edu.hfut.innovate.community.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service("topicService")
public class TopicServiceImpl extends ServiceImpl<TopicDao, TopicEntity> implements TopicService {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private TopicTagService topicTagService;
    @Autowired
    private LikeRecordService likeRecordService;
    @Autowired
    private CollectionRecordService collectionRecordService;

    @Override
    public PageUtils<TopicVo> queryPageByUserId(Map<String, Object> params, Long userId) {
        IPage<TopicEntity> page = this.page(
                new Query<TopicEntity>().getPage(params),
                new QueryWrapper<>()
        );

// 获取所有的TopicId
        Collection<Long> topicIds = CollectionUtil.getCollection(page.getRecords(), TopicEntity::getTopicId);
        // 根据TopicId分组
        Map<Long, List<CommentVo>> commentVoMap = commentService.mapByTopicIds(topicIds, 2, 0);

        Map<Long, UserVo> userVoMap = userService.mapByIds(
                // 获取所有的UserId
                CollectionUtil.getCollection(page.getRecords(), TopicEntity::getUserId));


        Map<Long, List<String>> tagMap = topicTagService.mapByTopicIds(topicIds);
        // 设置本人是否点赞，是否收藏
        Set<Long> likeSet = likeRecordService.setOfLikedDesIds(topicIds, userId, CommunityTypeUtil.TOPIC_TYPE);
        Set<Long> collectionSet = collectionRecordService.setOfCollectedTopics(topicIds, userId);


        List<TopicVo> list = page.getRecords().stream().map(topicEntity -> {
            TopicVo topicVo = BeanUtil.copyProperties(topicEntity, new TopicVo());
            topicVo.setComments(commentVoMap.get(topicEntity.getTopicId()));
            topicVo.setUser(userVoMap.get(topicEntity.getUserId()));
            topicVo.setTags(tagMap.get(topicEntity.getTopicId()));
            topicVo.setIsLiked(likeSet.contains(topicEntity.getTopicId()) ? 1 : 0);
            topicVo.setIsCollected(collectionSet.contains(topicEntity.getTopicId()) ? 1 : 0);

            return topicVo;
        }).toList();

        return new PageUtils<>(list, page.getTotal(), page.getSize(), page.getCurrent());
    }

    @Override
    public void removeTopicById(Long topicId) {
        List<CommentVo> commentVos = commentService.getByTopicId(topicId, ItemSize.ALL, ItemSize.NONE);

        this.removeById(topicId);

        if (commentVos != null && !commentVos.isEmpty()){
            commentService.removeAllByIdsWithReply(
                    CollectionUtil.getCollection(commentVos, CommentVo::getCommentId));
        }
    }

    @Override
    public void offsetTopicLikeCount(Long topicId, Integer offset) {
        update(new LambdaUpdateWrapper<TopicEntity>()
                .eq(TopicEntity::getTopicId, topicId)
                .setSql("likes = likes + " + offset));
    }

    @Override
    public TopicVo getTopicById(Long topicId, Long userId) {
        TopicEntity topicEntity = getById(topicId);
        if (topicEntity == null) {
            return null;
        }
        TopicVo topicVo = BeanUtil.copyProperties(topicEntity, new TopicVo());
        topicVo.setTags(topicTagService.getByTopicId(topicId));
        topicVo.setUser(BeanUtil.copyProperties(userService.getById(topicEntity.getUserId()), new UserVo()));
        topicVo.setComments(commentService.getByTopicIdWithLikes(topicId, userId, ItemSize.ALL, ItemSize.PARTS_BY_LIKES));
        topicVo.setIsLiked(likeRecordService.isLikedDesId(topicId, userId, CommunityTypeUtil.TOPIC_TYPE));


        return topicVo;
    }

    @Override
    public void offsetTopicCollectionCount(Long topicId, Integer offset) {
        update(new LambdaUpdateWrapper<TopicEntity>()
                .eq(TopicEntity::getTopicId, topicId)
                .setSql("collect = collect + " + offset));
    }

    @Override
    public Map<Long, TopicVo> mapByTopicIds(Collection<Long> topicIds) {

        List<TopicEntity> topicEntities = listByIds(topicIds);

        Collection<Long> userIds = CollectionUtil.getCollection(topicEntities, TopicEntity::getUserId);
        Map<Long, UserVo> userVoMap = userService.mapByIds(userIds);
        Map<Long, List<String>> tagMap = topicTagService.mapByTopicIds(topicIds);

        return topicEntities.stream().map(topicEntity -> {
            TopicVo topicVo = BeanUtil.copyProperties(topicEntity, new TopicVo());
            topicVo.setUser(userVoMap.get(topicEntity.getUserId()));
            topicVo.setTags(tagMap.get(topicEntity.getTopicId()));
            return topicVo;
        }).collect(Collectors.toMap(TopicVo::getTopicId, Function.identity()));

    }
}