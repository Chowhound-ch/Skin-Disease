package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.vo.community.CommentVo;
import edu.hfut.innovate.community.entity.CommentEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Chowhound
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils<CommentEntity> queryPage(Map<String, Object> params);

    /**
     * @author : Chowhound
     * @since : 2023/07/27 - 20:12
     * @param idSet : 话题id集合
     * @param size : 限制每个评论的数量，如果为null则不限制
     */
    Map<Long, List<CommentVo>> mapByTopicIds(Collection<Long> idSet, Integer size);


    List<CommentVo> getByTopicId(Long topicId);
}

