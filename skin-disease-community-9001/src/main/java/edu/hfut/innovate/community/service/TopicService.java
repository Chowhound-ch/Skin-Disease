package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.community.entity.TopicEntity;

import java.util.Map;

/**
 * 
 *
 * @author Chowhound
 */
public interface TopicService extends IService<TopicEntity> {

    PageUtils<TopicEntity> queryPage(Map<String, Object> params);

    void removeTopicById(Long topicId);

    void offsetTopicLikeCount(Long topicId, Integer offset);
}

