package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.domain.vo.community.ReplyVo;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.common.util.CommunityTypeUtil;
import edu.hfut.innovate.community.dao.ReplyMapper;
import edu.hfut.innovate.community.entity.ReplyEntity;
import edu.hfut.innovate.community.service.LikeRecordService;
import edu.hfut.innovate.community.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service("replyService")
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, ReplyEntity> implements ReplyService {
    @Autowired
    private LikeRecordService likeRecordService;

    @Override
    public Map<Long, List<ReplyVo>> mapByCommentIdsWithSizeOf(Collection<Long> idSet) {
        List<ReplyEntity> replyEntities = baseMapper.listByCommentIds(idSet);
        // 该条评论没有回复
        if (replyEntities.isEmpty()) {
            return Collections.emptyMap();
        }


        // 将entry.value转化为ReplyVo
        return replyEntities.stream()
                .map(entity-> BeanUtil.copyProperties(entity, new ReplyVo()))
                .collect(Collectors.groupingBy(ReplyVo::getCommentId));
    }

    @Override
    public List<ReplyVo> listByCommentId(Long commentId) {
        return mapByCommentIdsWithSizeOf(Collections.singleton(commentId))
                .getOrDefault(commentId, Collections.emptyList());
    }

    @Override
    public void removeByCommentId(Long commentId) {
        remove(new LambdaQueryWrapper<ReplyEntity>().eq(ReplyEntity::getCommentId, commentId));
    }

    @Override
    public void removeAllByCommentIds(Collection<Long> commentIds) {
        remove(new LambdaQueryWrapper<ReplyEntity>().in(ReplyEntity::getCommentId, commentIds));
    }

    @Override
    public void offsetReplyLikeCount(Long replyId, Integer offset) {
        update(new LambdaUpdateWrapper<ReplyEntity>()
                .eq(ReplyEntity::getReplyId, replyId)
                .setSql("likes = likes + " + offset));
    }

    @Override
    public List<ReplyVo> listByCommentIdWithLikes(Long commentId, Long userId) {
        List<ReplyVo> replyVos = listByCommentId(commentId);
        if (replyVos.isEmpty()) {
            return Collections.emptyList();
        }
        // 获取所有的点赞信息
        Collection<Long> ids = CollectionUtil.getCollection(replyVos, ReplyVo::getReplyId);
        Set<Long> likeSet = likeRecordService.setOfLikedDesIds(ids, userId, CommunityTypeUtil.REPLY_TYPE);

        replyVos.forEach(replyVo -> replyVo.setIsLiked(likeSet.contains(replyVo.getReplyId()) ? 1 : 0));
        return replyVos;
    }

}