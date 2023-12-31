package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.community.entity.TopicTagRelationEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Chowhound
 */
public interface TopicTagRelationService extends IService<TopicTagRelationEntity> {

    Map<Long, List<TopicTagRelationEntity>> mapByTopicIds(Collection<Long> topicIds);
}

