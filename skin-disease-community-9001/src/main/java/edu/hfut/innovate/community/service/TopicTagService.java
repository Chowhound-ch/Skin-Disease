package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.community.entity.TopicTagEntity;

import java.util.Map;

/**
 * 
 *
 * @author Chowhound
 */
public interface TopicTagService extends IService<TopicTagEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

