package edu.hfut.innovate.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.hfut.innovate.community.entity.ReplyEntity;
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
public interface ReplyMapper extends BaseMapper<ReplyEntity> {
    List<ReplyEntity> getByCommentId(@Param("commentId") Long commentId, @Param("number") Integer number);

    /**
     * k: commentId
     * @param commentIds
     * @return
     */
    List<ReplyEntity> listByCommentIds(@Param("commentIds") Collection<Long> commentIds);
}
