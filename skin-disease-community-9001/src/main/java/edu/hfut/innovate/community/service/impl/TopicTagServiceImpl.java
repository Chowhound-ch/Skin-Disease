package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.domain.vo.community.TopicTagVo;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.community.dao.TopicTagMapper;
import edu.hfut.innovate.community.entity.TopicTagEntity;
import edu.hfut.innovate.community.entity.TopicTagRelationEntity;
import edu.hfut.innovate.community.service.TopicTagRelationService;
import edu.hfut.innovate.community.service.TopicTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service("topicTagService")
public class TopicTagServiceImpl extends ServiceImpl<TopicTagMapper, TopicTagEntity> implements TopicTagService {
    @Autowired
    private TopicTagRelationService topicTagRelationService;

    @Override
    public Map<Long, List<TopicTagVo>> mapByTopicIds(Collection<Long> topicIds) {

        Map<Long, List<TopicTagRelationEntity>> mapByTopicIds = topicTagRelationService.mapByTopicIds(topicIds);
        Set<Long> tagIds = mapByTopicIds.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .map(TopicTagRelationEntity::getTagId)
                .collect(Collectors.toSet());
        if (tagIds.isEmpty()) {
            return Map.of();
        }
        Map<Long, TopicTagEntity> topicTagEntityMap = CollectionUtil.getMap(this.listByIds(tagIds), TopicTagEntity::getTagId);

        return mapByTopicIds.entrySet().stream()
                .map( entry -> Map.entry(entry.getKey(), entry.getValue().stream()
                        .map(topicTagRelationEntity ->
                                BeanUtil.copyProperties(topicTagEntityMap.get(topicTagRelationEntity.getTagId()), new TopicTagVo()))
                        .toList()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public List<TopicTagVo> getByTopicId(Long topicId) {
        return mapByTopicIds(Set.of(topicId)).get(topicId);
    }

}