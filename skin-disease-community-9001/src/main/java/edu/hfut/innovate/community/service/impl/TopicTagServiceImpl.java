package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.community.dao.TopicTagDao;
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
public class TopicTagServiceImpl extends ServiceImpl<TopicTagDao, TopicTagEntity> implements TopicTagService {
    @Autowired
    private TopicTagRelationService topicTagRelationService;

    @Override
    public Map<Long, List<String>> mapByTopicIds(Collection<Long> topicIds) {

        Map<Long, List<TopicTagRelationEntity>> mapByTopicIds = topicTagRelationService.mapByTopicIds(topicIds);
        Set<Long> tagIds = mapByTopicIds.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .map(TopicTagRelationEntity::getTagId)
                .collect(Collectors.toSet());

        Map<Long, TopicTagEntity> topicTagEntityMap = CollectionUtil.getMap(this.listByIds(tagIds), TopicTagEntity::getTagId);

        return mapByTopicIds.entrySet().stream().map( entry -> Map.entry(entry.getKey(), entry.getValue().stream()
                .map(topicTagRelationEntity -> topicTagEntityMap.get(topicTagRelationEntity.getTagId()).getName())
                .collect(Collectors.toList()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public List<String> getByTopicId(Long topicId) {
        return mapByTopicIds(Set.of(topicId)).get(topicId);
    }

}