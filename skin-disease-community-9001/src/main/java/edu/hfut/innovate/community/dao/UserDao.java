package edu.hfut.innovate.community.dao;

import edu.hfut.innovate.community.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author Chowhound
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
}
