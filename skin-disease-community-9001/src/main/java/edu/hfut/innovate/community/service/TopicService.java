package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.domain.vo.community.TopicVo;
import edu.hfut.innovate.community.entity.TopicEntity;

import java.util.Collection;
import java.util.Map;

/**
 * 
 *
 * @author Chowhound
 */
public interface TopicService extends IService<TopicEntity> {

    PageUtils<TopicVo> queryPageByUserId(Map<String, Object> params, Long userId);

    void removeTopicById(Long topicId);

    void offsetTopicLikeCount(Long topicId, Integer offset);

    TopicVo getTopicById(Long topicId, Long userId);

    void offsetTopicCollectionCount(Long topicId, Integer offset);

    Map<Long, TopicVo> mapByTopicIds(Collection<Long> topicIds);

    void addForwardCount(Long topicId);
}

