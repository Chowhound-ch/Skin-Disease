package edu.hfut.innovate.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.hfut.innovate.community.entity.TopicEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author Chowhound
 */
@Mapper
public interface TopicMapper extends BaseMapper<TopicEntity> {
	TopicEntity getTopicById(@Param("topicId")Long topicId);

	List<TopicEntity> listTopics(@Param("page") Integer page, @Param("limit") Integer limit, @Param("locationId") Long locationId);
}
