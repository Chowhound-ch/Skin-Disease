package edu.hfut.innovate.community.service;

import edu.hfut.innovate.common.domain.vo.community.TopicLocationVo;
import edu.hfut.innovate.community.entity.TopicLocationEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.Map;

/**
* @author hh825
* @description 针对表【topic_location】的数据库操作Service
* @createDate 2023-08-06 14:18:25
*/
public interface TopicLocationService extends IService<TopicLocationEntity> {

    Map<Long, TopicLocationVo> mapTopicLocationById(Collection<Long> topicIds);
}
