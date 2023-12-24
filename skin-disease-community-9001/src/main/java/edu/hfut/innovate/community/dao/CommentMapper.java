package edu.hfut.innovate.community.dao;

import edu.hfut.innovate.community.entity.CommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 
 * 
 * @author Chowhound
 */
@Mapper
public interface CommentMapper extends BaseMapper<CommentEntity> {
    List<CommentEntity> listByTopicIds(@Param("topicIds") Collection<Long> topicIds);
}
