package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.community.dao.TopicTagRelationDao;
import edu.hfut.innovate.community.entity.TopicTagRelationEntity;
import edu.hfut.innovate.community.service.TopicTagRelationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("topicTagRelationService")
public class TopicTagRelationServiceImpl extends ServiceImpl<TopicTagRelationDao, TopicTagRelationEntity> implements TopicTagRelationService {

    @Override
    public Map<Long, List<TopicTagRelationEntity>> mapByTopicIds(Collection<Long> topicIds) {
        return this.list(new LambdaQueryWrapper<TopicTagRelationEntity>()
                        .in(TopicTagRelationEntity::getTopicId, topicIds)).stream()
                .collect(Collectors.groupingBy(TopicTagRelationEntity::getTopicId));
    }

}