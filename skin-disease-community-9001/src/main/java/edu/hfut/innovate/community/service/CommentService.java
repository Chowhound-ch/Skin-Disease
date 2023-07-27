package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.vo.community.CommentVo;
import edu.hfut.innovate.community.entity.CommentEntity;
import edu.hfut.innovate.community.entity.TopicEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 *
 * @author Chowhound
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils<CommentEntity> queryPage(Map<String, Object> params);

    List<CommentVo> listByTopicIds(Collection<Long> idSet);


}

