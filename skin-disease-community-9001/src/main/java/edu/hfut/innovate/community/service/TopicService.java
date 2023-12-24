package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.domain.es.HighlightVo;
import edu.hfut.innovate.common.domain.vo.community.TopicVo;
import edu.hfut.innovate.community.entity.TopicEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Chowhound
 */
public interface TopicService extends IService<TopicEntity> {

    List<TopicVo> queryPageByUserId(Integer page, Integer limit, Long userId, Long locationId);

    void removeTopicById(Long topicId);

    void offsetTopicLikeCount(Long topicId, Integer offset);

    TopicVo getTopicByIdWithLikeInfo(Long topicId, Long userId);

    void offsetTopicCollectionCount(Long topicId, Integer offset);

    Map<Long, TopicVo> mapByTopicIds(Collection<Long> topicIds);

    void addForwardCount(Long topicId);

    List<HighlightVo<TopicVo>> search(String keyword, Long userId);

    TopicVo getTopicById(Long topicId);

    TopicVo getTopicByIdWithAnonymous(Long topicId);
}

