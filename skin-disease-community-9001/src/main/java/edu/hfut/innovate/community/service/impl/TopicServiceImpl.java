package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.Query;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.common.util.ItemSize;
import edu.hfut.innovate.common.vo.community.CommentVo;
import edu.hfut.innovate.common.vo.community.TopicVo;
import edu.hfut.innovate.common.vo.community.UserVo;
import edu.hfut.innovate.community.dao.TopicDao;
import edu.hfut.innovate.community.entity.TopicEntity;
import edu.hfut.innovate.community.entity.UserEntity;
import edu.hfut.innovate.community.service.CommentService;
import edu.hfut.innovate.community.service.TopicService;
import edu.hfut.innovate.community.service.TopicTagService;
import edu.hfut.innovate.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("topicService")
public class TopicServiceImpl extends ServiceImpl<TopicDao, TopicEntity> implements TopicService {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private TopicTagService topicTagService;
    @Override
    public PageUtils<TopicVo> queryPageByUserId(Map<String, Object> params, Long userId) {
        IPage<TopicEntity> page = this.page(
                new Query<TopicEntity>().getPage(params),
                new QueryWrapper<>()
        );

// 获取所有的TopicId
        Collection<Long> topicIds = CollectionUtil.getCollection(page.getRecords(), TopicEntity::getTopicId);
        // 根据TopicId分组
        Map<Long, List<CommentVo>> commentVoMap = commentService.mapByTopicIds(topicIds, 2, 0);

        Map<Long, UserEntity> userEntityMap = userService.mapByIds(
                // 获取所有的UserId
                CollectionUtil.getCollection(page.getRecords(), TopicEntity::getUserId));
        // 将UserEntity转换为UserVo
        Map<Long, UserVo> userVoMap = userEntityMap.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), BeanUtil.copyProperties(entry.getValue(), new UserVo())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Long, List<String>> tagMap = topicTagService.mapByTopicIds(topicIds);

        List<TopicVo> list = page.getRecords().stream().map(topicEntity -> {
            TopicVo topicVo = BeanUtil.copyProperties(topicEntity, new TopicVo());
            topicVo.setComments(commentVoMap.get(topicEntity.getTopicId()));
            topicVo.setUser(userVoMap.get(topicEntity.getUserId()));
            topicVo.setTags(tagMap.get(topicEntity.getTopicId()));

            return topicVo;
        }).toList();

        return new PageUtils<>(list, page.getTotal(), page.getSize(), page.getCurrent());
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