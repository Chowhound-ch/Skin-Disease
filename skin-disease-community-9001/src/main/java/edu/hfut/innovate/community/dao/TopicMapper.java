package edu.hfut.innovate.community.dao;

import edu.hfut.innovate.community.entity.TopicEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author Chowhound
 */
@Mapper
public interface TopicMapper extends BaseMapper<TopicEntity> {
	TopicEntity selectOneTopic(@Param("topicId")Long topicId);

}
