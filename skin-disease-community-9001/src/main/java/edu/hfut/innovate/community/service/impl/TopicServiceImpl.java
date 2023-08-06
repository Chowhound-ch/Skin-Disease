package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.domain.vo.community.*;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.Query;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.common.util.CommunityTypeUtil;
import edu.hfut.innovate.common.util.ItemSize;
import edu.hfut.innovate.community.dao.TopicDao;
import edu.hfut.innovate.community.entity.TopicEntity;
import edu.hfut.innovate.community.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service("topicService")
public class TopicServiceImpl extends ServiceImpl<TopicDao, TopicEntity> implements TopicService {
    // region 依赖注入
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
    @Autowired
    private TopicLocationService topicLocationService;
    // endregion

    @Transactional
    @Override
    public PageUtils<TopicVo> queryPageByUserId(Map<String, Object> params, Long userId, Long locationId) {
        IPage<TopicEntity> page = this.page(
                new Query<TopicEntity>().getPage(params),
                new LambdaUpdateWrapper<TopicEntity>().eq(locationId != null, TopicEntity::getLocationId, locationId)
        );
        // 获取所有的TopicId
        Collection<Long> topicIds = new HashSet<>();
        Collection<Long> userIds = new HashSet<>();
        Collection<Long> locationIds = new HashSet<>();

        for (TopicEntity record : page.getRecords()) {
            topicIds.add(record.getTopicId());
            userIds.add(record.getUserId());
            // 如果locationId为空则是按locationId查询，所有的locationId都是一样的
            if (locationId == null) {
                locationIds.add(record.getLocationId());
            }
        }

        // 根据TopicId分组
        Map<Long, List<CommentVo>> commentVoMap = commentService.mapByTopicIds(topicIds, 2, 0);
        // 获取所有的UserId
        Map<Long, UserVo> userVoMap = userService.mapByIds(userIds);


        Map<Long, List<TopicTagVo>> tagMap = topicTagService.mapByTopicIds(topicIds);
        // 设置本人是否点赞，是否收藏
        Set<Long> likeSet = likeRecordService.setOfLikedDesIds(topicIds, userId, CommunityTypeUtil.TOPIC_TYPE);
        Set<Long> collectionSet = collectionRecordService.setOfCollectedTopics(topicIds, userId);


        // 8.6 设置location
        Map<Long, TopicLocationVo> locationMap;
        if (locationId == null) {
            locationMap = topicLocationService.listByIds(locationIds).stream()
                    .map(topicLocationEntity -> BeanUtil.copyProperties(topicLocationEntity, new TopicLocationVo()))
                    .collect(Collectors.toMap(TopicLocationVo::getLocationId, Function.identity()));
        } else {
            locationMap = new HashMap<>(1);
            locationMap.put(locationId,
                    BeanUtil.copyProperties(topicLocationService.getById(locationId), new TopicLocationVo()));
        }

        Map<Long, TopicLocationVo> finalLocationMap = locationMap;
        List<TopicVo> list = page.getRecords().stream().map(topicEntity -> {
            TopicVo topicVo = BeanUtil.copyProperties(topicEntity, new TopicVo());
            topicVo.setComments(commentVoMap.get(topicEntity.getTopicId()));
            topicVo.setUser(userVoMap.get(topicEntity.getUserId()));
            topicVo.setTags(tagMap.get(topicEntity.getTopicId()));
            topicVo.setIsLiked(likeSet.contains(topicEntity.getTopicId()) ? 1 : 0);
            topicVo.setIsCollected(collectionSet.contains(topicEntity.getTopicId()) ? 1 : 0);
            topicVo.setLocation(finalLocationMap.get(topicEntity.getLocationId()));

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
        UserVo userVo = topicVo.getIsAnonymous() == 1 ? UserVo.anonymousUser() :
                BeanUtil.copyProperties(userService.getById(topicEntity.getUserId()), new UserVo());

        topicVo.setUser(userVo);
        topicVo.setComments(commentService.getByTopicIdWithLikes(topicId, userId, ItemSize.ALL, ItemSize.PARTS_BY_LIKES));
        topicVo.setIsLiked(likeRecordService.isLikedDesId(topicId, userId, CommunityTypeUtil.TOPIC_TYPE));
        topicVo.setIsCollected(collectionRecordService.isCollectedTopic(topicId, userId));
        // 8.6 设置location
        topicVo.setLocation(BeanUtil.copyProperties(
                topicLocationService.getById(topicEntity.getLocationId()), new TopicLocationVo()));

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

        if (topicIds == null || topicIds.isEmpty()) {
            return Collections.emptyMap();
        }

        List<TopicEntity> topicEntities = listByIds(topicIds);

        Collection<Long> userIds = new HashSet<>();
        Collection<Long> locationIds = new HashSet<>();
        for (TopicEntity topicEntity : topicEntities) {
            userIds.add(topicEntity.getUserId());
            locationIds.add(topicEntity.getLocationId());
        }

        userIds = CollectionUtil.getCollection(topicEntities, TopicEntity::getUserId);
        Map<Long, UserVo> userVoMap = userService.mapByIds(userIds);
        Map<Long, List<TopicTagVo>> tagMap = topicTagService.mapByTopicIds(topicIds);

        // 8.6 设置location
        Map<Long, TopicLocationVo> locationMap = topicLocationService.mapTopicLocationById(locationIds);

        return topicEntities.stream().map(topicEntity -> {
            TopicVo topicVo = BeanUtil.copyProperties(topicEntity, new TopicVo());
            topicVo.setUser(userVoMap.get(topicEntity.getUserId()));
            topicVo.setTags(tagMap.get(topicEntity.getTopicId()));
            topicVo.setLocation(locationMap.get(topicEntity.getLocationId()));
            return topicVo;
        }).collect(Collectors.toMap(TopicVo::getTopicId, Function.identity()));

    }

    @Override
    public void addForwardCount(Long topicId) {
        update(new LambdaUpdateWrapper<TopicEntity>()
                .eq(TopicEntity::getTopicId, topicId)
                .setSql("forward = forward + 1"));
    }
}