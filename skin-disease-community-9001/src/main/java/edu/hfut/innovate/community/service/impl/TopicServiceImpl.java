package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.domain.es.ElasticTopicVo;
import edu.hfut.innovate.common.domain.es.HighlightVo;
import edu.hfut.innovate.common.domain.vo.community.*;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.common.util.CommunityTypeUtil;
import edu.hfut.innovate.community.dao.TopicMapper;
import edu.hfut.innovate.community.entity.TopicEntity;
import edu.hfut.innovate.community.service.*;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service("topicService")
public class TopicServiceImpl extends ServiceImpl<TopicMapper, TopicEntity> implements TopicService {
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
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    // endregion

    @Transactional
    @Override
    public List<TopicVo> queryPageByUserId(Integer page, Integer limit, Long userId, Long locationId, Integer sort) {
        List<TopicEntity> topicEntities = baseMapper
                .listTopics(page == null ? 0 : (page - 1) * limit, limit == null ? 10 : limit, locationId, sort);
        // 获取所有的TopicId
        Collection<Long> topicIds = CollectionUtil.getCollection(topicEntities, TopicEntity::getTopicId);

        // 根据TopicId分组
        Map<Long, List<CommentVo>> commentVoMap = commentService.mapByTopicIds(topicIds);


        // 设置本人是否点赞，是否收藏
        Set<Long> likeSet = likeRecordService.setOfLikedDesIds(topicIds, userId, CommunityTypeUtil.TOPIC_TYPE);
        Set<Long> collectionSet = collectionRecordService.setOfCollectedTopics(topicIds, userId);

        return topicEntities.stream().map(topicEntity -> {
            TopicVo topicVo = BeanUtil.copyProperties(topicEntity, new TopicVo());
            topicVo.setComments(commentVoMap.get(topicEntity.getTopicId()));
            if (topicVo.getUser() == null) topicVo.setUser(BeanUtil.copyProperties(topicEntity.getUser(), new UserVo()));
            topicVo.setIsLiked(likeSet.contains(topicEntity.getTopicId()) ? 1 : 0);
            topicVo.setIsCollected(collectionSet.contains(topicEntity.getTopicId()) ? 1 : 0);
            return topicVo;
        }).toList();
    }

    @Override
    public void removeTopicById(Long topicId) {
        List<Long> ids = commentService.listIdByTopicId(topicId);

        this.removeById(topicId);

        if (ids != null && !ids.isEmpty()){
            commentService.removeAllByIdsWithReply(ids);
        }
    }

    @Override
    public void offsetTopicLikeCount(Long topicId, Integer offset) {
        update(new LambdaUpdateWrapper<TopicEntity>()
                .eq(TopicEntity::getTopicId, topicId)
                .setSql("likes = likes + " + offset));
    }

    @Override
    public TopicVo getTopicByIdWithLikeInfo(Long topicId, Long userId) {
        TopicVo topicVo = getTopicByIdWithAnonymous(topicId);
        // TODO
        topicVo.setComments(commentService.getByTopicIdWithLikes(topicId, userId));
        topicVo.setIsLiked(likeRecordService.isLikedDesId(topicId, userId, CommunityTypeUtil.TOPIC_TYPE));
        topicVo.setIsCollected(collectionRecordService.isCollectedTopic(topicId, userId));

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
//            userIds.add(topicEntity.getUser());
//            locationIds.add(topicEntity.getLocationId());
        }

//        userIds = CollectionUtil.getCollection(topicEntities, TopicEntity::getUser);
        Map<Long, UserVo> userVoMap = userService.mapByIds(userIds);
        Map<Long, List<TopicTagVo>> tagMap = topicTagService.mapByTopicIds(topicIds);

        // 8.6 设置location
        Map<Long, TopicLocationVo> locationMap = topicLocationService.mapTopicLocationById(locationIds);

        return topicEntities.stream().map(topicEntity -> {
            TopicVo topicVo = BeanUtil.copyProperties(topicEntity, new TopicVo());
            topicVo.setUser(userVoMap.get(topicEntity.getUser()));
            topicVo.setTags(tagMap.get(topicEntity.getTopicId()));
//            topicVo.setLocation(locationMap.get(topicEntity.getLocationId()));
            return topicVo;
        }).collect(Collectors.toMap(TopicVo::getTopicId, Function.identity()));

    }

    @Override
    public void addForwardCount(Long topicId) {
        update(new LambdaUpdateWrapper<TopicEntity>()
                .eq(TopicEntity::getTopicId, topicId)
                .setSql("forward = forward + 1"));
    }

    @Override
    public List<HighlightVo<TopicVo>> search(String keyword, Long userId) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        builder.withQuery(QueryBuilders.multiMatchQuery(keyword,
                "title", "content", "tags.name", "user.username", "comments.content"));

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("title").field("content").field("tags.name")
                .field("user.username").field("comments.content")
                .preTags("<span style='color:red'>").postTags("</span>");
        builder.withHighlightBuilder(highlightBuilder);
        SearchHits<ElasticTopicVo> hits = elasticsearchTemplate.search(builder.build(), ElasticTopicVo.class);

        return hits.get().map(hit -> {
            ElasticTopicVo elasticTopicVo = hit.getContent();
            TopicVo topicVo = BeanUtil.copyProperties(elasticTopicVo, new TopicVo());

            topicVo.setUser(BeanUtil.copyProperties(elasticTopicVo.getUser(), new UserVo()));
            topicVo.setTags(BeanUtil.copyPropertiesList(elasticTopicVo.getTags(), TopicTagVo.class));
            topicVo.setLocation(BeanUtil.copyProperties(elasticTopicVo.getLocation(), new TopicLocationVo()));

            List<CommentVo> commentVos = elasticTopicVo.getComments().stream().map(elasticCommentVo -> {
                CommentVo commentVo = BeanUtil.copyProperties(elasticCommentVo, new CommentVo());
                commentVo.setUser(BeanUtil.copyProperties(elasticCommentVo.getUser(), new UserVo()));
                return commentVo;
            }).toList();

            topicVo.setComments(commentVos);
            return new HighlightVo<>(topicVo, hit.getHighlightFields());
        }).toList();
    }

    /**
     * 包含user tags location
     * 不包含comments
     * 159ms
     * @param topicId topicId
     * @return topicVo
     */
    @Override
    public TopicVo getTopicById(Long topicId) {
        return BeanUtil.copyProperties(baseMapper.getTopicById(topicId), new TopicVo());
    }

    @Override
    public TopicVo getTopicByIdWithAnonymous(Long topicId) {
        TopicVo topic = getTopicById(topicId);
        if (topic.getIsAnonymous() == 1) {
            topic.setUser(UserVo.anonymousUser());
        }
        return topic;
    }
}