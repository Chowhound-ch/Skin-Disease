package edu.hfut.innovate.community.service.impl;

import edu.hfut.innovate.common.domain.es.ElasticTopicVo;
import edu.hfut.innovate.community.dao.TopicESRepository;
import edu.hfut.innovate.community.service.TopicESService;
import edu.hfut.innovate.community.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * @author : Chowhound
 * @since : 2024/4/6 - 18:33
 */
@Slf4j
@Service
public class TopicESServiceImpl implements TopicESService {

    private final TopicESRepository repository;
    private final TopicService service;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    public TopicESServiceImpl(TopicESRepository repository, TopicService service) {
        this.repository = repository;
        this.service = service;
    }

    @Override
    public void update(ElasticTopicVo topicVo) {
        try {
            repository.save(topicVo);
        } catch (Exception e) {
            String msg = e.getMessage();
            if (!msg.contains("Created")&&!msg.contains("200 OK")){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void save(ElasticTopicVo topicVo) {
        try {
            repository.save(topicVo);
        } catch (Exception e) {
            String msg = e.getMessage();
            if (!msg.contains("Created")&&!msg.contains("200 OK")){
                log.error("es新增文档失败，异常信息：", e);
                throw new RuntimeException(e);
            }
        }

    }

    public ElasticTopicVo getByTopicId(Long topicId) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("topicId", topicId);
        builder.withQuery(queryBuilder);
        SearchHits<ElasticTopicVo> search = elasticsearchTemplate.search(builder.build(), ElasticTopicVo.class);
        if (search.getTotalHits() == 0) {
            return null;
        }
        SearchHit<ElasticTopicVo> searchHit = search.getSearchHit(0);
        return searchHit.getContent();
    }

    @Override
    public void delete(Long topicId) {
        try {
            NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

            TermQueryBuilder queryBuilder = QueryBuilders.termQuery("topicId", topicId);
            builder.withQuery(queryBuilder);
            elasticsearchTemplate.delete(builder.build(), ElasticTopicVo.class);
        } catch (Exception e) {
            String msg = e.getMessage();
            if (!msg.contains("Created")&&!msg.contains("200 OK")){
                throw new RuntimeException(e);
            }
        }
    }
}
