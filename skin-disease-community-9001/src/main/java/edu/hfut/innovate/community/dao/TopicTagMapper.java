package edu.hfut.innovate.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.hfut.innovate.community.entity.TopicTagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author Chowhound
 */
@Mapper
public interface TopicTagMapper extends BaseMapper<TopicTagEntity> {

    List<TopicTagEntity> listByTopicId(@Param("topicId") Long topicId);

}
