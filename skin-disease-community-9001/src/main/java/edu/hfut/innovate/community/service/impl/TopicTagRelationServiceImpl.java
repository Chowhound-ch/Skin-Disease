package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import edu.hfut.innovate.community.dao.TopicTagRelationDao;
import edu.hfut.innovate.community.entity.TopicTagRelationEntity;
import edu.hfut.innovate.community.service.TopicTagRelationService;


@Service("topicTagRelationService")
public class TopicTagRelationServiceImpl extends ServiceImpl<TopicTagRelationDao, TopicTagRelationEntity> implements TopicTagRelationService {

    @Override
    public PageUtils<TopicTagRelationEntity> queryPage(Map<String, Object> params) {
        IPage<TopicTagRelationEntity> page = this.page(
                new Query<TopicTagRelationEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils<>(page);
    }

    @Override
    public Map<Long, List<TopicTagRelationEntity>> mapByTopicIds(Collection<Long> topicIds) {
        return this.list(new LambdaQueryWrapper<TopicTagRelationEntity>()
                        .in(TopicTagRelationEntity::getTopicId, topicIds)).stream()
                .collect(Collectors.groupingBy(TopicTagRelationEntity::getTopicId));
    }

}