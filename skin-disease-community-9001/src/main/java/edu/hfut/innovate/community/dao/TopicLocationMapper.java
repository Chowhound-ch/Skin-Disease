package edu.hfut.innovate.community.dao;

import edu.hfut.innovate.community.entity.TopicLocationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TopicLocationMapper extends BaseMapper<TopicLocationEntity> {

    TopicLocationEntity getLocationById(@Param("locationId")Long locationId);

}




