package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.vo.community.ReplyVo;
import edu.hfut.innovate.community.entity.ReplyEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Chowhound
 */
public interface ReplyService extends IService<ReplyEntity> {

    PageUtils queryPage(Map<String, Object> params);


    Map<Long, List<ReplyVo>> mapByCommentIdsWithSizeOf(Collection<Long> idSet, Integer size);

    List<ReplyVo> listByCommentId(Long commentId);

    void removeByCommentId(Long commentId);
}

