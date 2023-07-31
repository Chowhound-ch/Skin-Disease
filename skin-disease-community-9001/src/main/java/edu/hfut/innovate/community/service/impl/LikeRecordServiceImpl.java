package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.Query;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.common.util.CommunityTypeUtil;
import edu.hfut.innovate.common.vo.community.LikeRecordVo;
import edu.hfut.innovate.common.vo.community.UserVo;
import edu.hfut.innovate.community.entity.LikeRecord;
import edu.hfut.innovate.community.entity.TopicEntity;
import edu.hfut.innovate.community.service.CommentService;
import edu.hfut.innovate.community.service.LikeRecordService;
import edu.hfut.innovate.community.dao.LikeRecordMapper;
import edu.hfut.innovate.community.service.ReplyService;
import edu.hfut.innovate.community.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LikeRecordServiceImpl extends ServiceImpl<LikeRecordMapper, LikeRecord>
    implements LikeRecordService{
    @Autowired
    private TopicService topicService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;

    @Override
    public PageUtils<LikeRecordVo> queryPageByUserId(Map<String, Object> params, Long userId) {
        IPage<LikeRecord> page = this.page(
                new Query<LikeRecord>().getPage(params),
                new LambdaUpdateWrapper<LikeRecord>().eq(LikeRecord::getUserId, userId)
        );

        List<LikeRecordVo> likeRecordVos = page.getRecords().stream()
                .map(likeRecord -> BeanUtil.copyProperties(likeRecord, new LikeRecordVo()))
                .toList();
        return new PageUtils<>(likeRecordVos, page.getTotal(), page.getSize(), page.getCurrent());
    }

    @Override
    public Set<Long> setOfLikedTopics(Collection<Long> topicIds, Long userId) {
        List<LikeRecord> likeRecords = list(new LambdaQueryWrapper<LikeRecord>()
                .in(LikeRecord::getDesId, topicIds)
                .eq(LikeRecord::getDesType, CommunityTypeUtil.TOPIC_TYPE)
                .eq(LikeRecord::getUserId, userId)
        );

        return likeRecords.stream().map(LikeRecord::getDesId).collect(Collectors.toSet());
    }


    @Transactional
    @Override
    public void saveLikeRecord(LikeRecord likeRecord) {
        saveOrUpdate(likeRecord, getEQWrapper(likeRecord.getUserId(), likeRecord.getDesId(), likeRecord.getDesType()));

        if (Objects.equals(likeRecord.getDesType(), CommunityTypeUtil.TOPIC_TYPE)) {
            topicService.offsetTopicLikeCount(likeRecord.getDesId(), 1);
        } else if (Objects.equals(likeRecord.getDesType(), CommunityTypeUtil.COMMENT_TYPE)) {
            commentService.offsetCommentLikeCount(likeRecord.getDesId(), 1);
        } else if (Objects.equals(likeRecord.getDesType(), CommunityTypeUtil.REPLY_TYPE)) {
            replyService.offsetReplyLikeCount(likeRecord.getDesId(), 1);
        }
    }

    @Override
    public LikeRecord getLikeRecord(Long userId, Long desId, Integer desType) {
        return getOne(new LambdaQueryWrapper<LikeRecord>().eq(LikeRecord::getUserId, userId)
                .eq(LikeRecord::getDesId, desId)
                .eq(LikeRecord::getDesType, desType));
    }

    @Override
    public List<LikeRecord> listTopicLikedByUserId(Long userId) {
        return list(new LambdaQueryWrapper<LikeRecord>().eq(LikeRecord::getUserId, userId));
    }

    @Override
    public void removeLikeRecord(Long likeId) {
        LikeRecord likeRecord = getById(likeId);
        if (Objects.equals(likeRecord.getDesType(), CommunityTypeUtil.TOPIC_TYPE)) {
            topicService.offsetTopicLikeCount(likeRecord.getDesId(), -1);
        } else if (Objects.equals(likeRecord.getDesType(), CommunityTypeUtil.COMMENT_TYPE)) {
            commentService.offsetCommentLikeCount(likeRecord.getDesId(), -1);
        } else if (Objects.equals(likeRecord.getDesType(), CommunityTypeUtil.REPLY_TYPE)) {
            replyService.offsetReplyLikeCount(likeRecord.getDesId(), -1);
        }
    }


    private LambdaQueryWrapper<LikeRecord> getEQWrapper(Long userId, Long desId, Integer desType) {
        return new LambdaQueryWrapper<LikeRecord>().eq(LikeRecord::getUserId, userId)
                .eq(LikeRecord::getDesId, desId)
                .eq(LikeRecord::getDesType, desType);
    }
}




