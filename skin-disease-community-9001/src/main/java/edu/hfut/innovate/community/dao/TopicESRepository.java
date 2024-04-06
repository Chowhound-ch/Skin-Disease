package edu.hfut.innovate.community.dao;

import edu.hfut.innovate.common.domain.es.ElasticTopicVo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TopicESRepository extends ElasticsearchRepository<ElasticTopicVo, Long> {

}