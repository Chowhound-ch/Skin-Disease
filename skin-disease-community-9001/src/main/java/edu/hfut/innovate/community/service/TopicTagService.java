package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.domain.vo.community.TopicTagVo;
import edu.hfut.innovate.community.entity.TopicTagEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Chowhound
 */
public interface TopicTagService extends IService<TopicTagEntity> {

    Map<Long, List<TopicTagVo>> mapByTopicIds(Collection<Long> topicIds);

    List<TopicTagVo> getByTopicId(Long topicId);
}


