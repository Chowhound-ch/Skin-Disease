package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.Query;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.common.util.ItemSize;
import edu.hfut.innovate.common.vo.community.CommentVo;
import edu.hfut.innovate.community.entity.ReplyEntity;
import edu.hfut.innovate.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import edu.hfut.innovate.community.dao.TopicDao;
import edu.hfut.innovate.community.entity.TopicEntity;
import edu.hfut.innovate.community.service.TopicService;


@Service("topicService")
public class TopicServiceImpl extends ServiceImpl<TopicDao, TopicEntity> implements TopicService {
    @Autowired
    private CommentService commentService;
    @Override
    public PageUtils<TopicEntity> queryPage(Map<String, Object> params) {
        IPage<TopicEntity> page = this.page(
                new Query<TopicEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils<>(page);
    }

    @Override
    public void removeTopicById(Long topicId) {
        List<CommentVo> commentVos = commentService.getByTopicId(topicId, ItemSize.ALL, ItemSize.NONE);

        this.removeById(topicId);

        if (commentVos != null && !commentVos.isEmpty()){
            commentService.removeAllByIdsWithReply(
                    CollectionUtil.getCollection(commentVos, CommentVo::getCommentId));
        }
    }

    @Override
    public void offsetTopicLikeCount(Long topicId, Integer offset) {
        update(new LambdaUpdateWrapper<TopicEntity>()
                .eq(TopicEntity::getTopicId, topicId)
                .setSql("likes = likes + " + offset));
    }

}