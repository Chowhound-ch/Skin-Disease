package edu.hfut.innovate.community.service;

import edu.hfut.innovate.common.domain.es.ElasticTopicVo;

/**
 * @author : Chowhound
 * @since : 2024/4/6 - 18:32
 */
public interface TopicESService {
    void update(ElasticTopicVo topicVo);
    void save(ElasticTopicVo topicVo);
    void delete(Long topicId);
    ElasticTopicVo getByTopicId(Long topicId);
}
