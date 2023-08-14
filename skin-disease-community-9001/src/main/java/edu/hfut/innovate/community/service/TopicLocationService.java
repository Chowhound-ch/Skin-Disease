package edu.hfut.innovate.community.service;

import edu.hfut.innovate.common.domain.vo.community.TopicLocationVo;
import edu.hfut.innovate.community.entity.TopicLocationEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.Map;

public interface TopicLocationService extends IService<TopicLocationEntity> {

    Map<Long, TopicLocationVo> mapTopicLocationById(Collection<Long> topicIds);
}
