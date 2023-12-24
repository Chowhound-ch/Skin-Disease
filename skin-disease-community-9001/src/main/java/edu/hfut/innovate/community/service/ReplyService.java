package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.domain.vo.community.ReplyVo;
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

    Map<Long, List<ReplyVo>> mapByCommentIdsWithSizeOf(Collection<Long> idSet);

    List<ReplyVo> listByCommentId(Long commentId);

    void removeByCommentId(Long commentId);

    void removeAllByCommentIds(Collection<Long> commentIds);

    void offsetReplyLikeCount(Long replyId, Integer offset);

    List<ReplyVo> listByCommentIdWithLikes(Long commentId, Long userId);
}

