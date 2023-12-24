package edu.hfut.innovate.community.dao;

import edu.hfut.innovate.community.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author Chowhound
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
    UserEntity getUserById(@Param("userId") Long userId);
	
}
