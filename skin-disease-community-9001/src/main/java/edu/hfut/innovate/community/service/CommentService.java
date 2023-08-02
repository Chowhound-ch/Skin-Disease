package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.domain.vo.community.CommentVo;
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
     * @param commentItemSize : 限制每个评论的数量，如果为null则不限制
     * @param replyItemSize : 限制每个回复的数量，如果为null则不限制
     */
    Map<Long, List<CommentVo>> mapByTopicIds(Collection<Long> idSet, Integer commentItemSize, Integer replyItemSize);

    /**根据话题id获取评论,不限制评论数量，默认限制reply数量为2
     *
     * @author : Chowhound
     * @since : 2023/07/28 - 17:11
     */


    void removeByIdWithReply(Long commentId);


    void removeAllByIdsWithReply(Collection<Long> commentIds);

    void offsetCommentLikeCount(Long commentId, Integer offset);

    List<CommentVo> getByTopicId(Long topicId, Integer commentItemSize, Integer replyItemSize);


    List<CommentVo> getByTopicIdWithLikes(Long topicId, Long userId, Integer commentItemSize, Integer replyItemSize);

    CommentVo getCommentById(Long commentId, Long userId);

//    void addLike(Long userId, Long likedId);
}

