package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.Query;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.vo.community.CommentVo;
import edu.hfut.innovate.common.vo.community.ReplyVo;
import edu.hfut.innovate.common.vo.community.UserVo;
import edu.hfut.innovate.community.dao.CommentDao;
import edu.hfut.innovate.community.entity.CommentEntity;
import edu.hfut.innovate.community.service.CommentService;
import edu.hfut.innovate.community.service.ReplyService;
import edu.hfut.innovate.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserService userService;

    @Override
    public PageUtils<CommentEntity> queryPage(Map<String, Object> params) {
        IPage<CommentEntity> page = this.page(
                new Query<CommentEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils<>(page);
    }

    @Override
    public List<CommentVo> listByTopicIds(Collection<Long> idSet) {
        List<CommentEntity> commentEntities = this.list(
                new LambdaQueryWrapper<CommentEntity>().in(CommentEntity::getTopicId, idSet));

        // 获取所有的评论id
        Collection<Long> commentIds = new HashSet<>();
        // 获取所有的用户id
        Collection<Long> userIds = new HashSet<>();
        commentEntities.forEach(commentEntity -> {
            commentIds.add(commentEntity.getCommentId());
            userIds.add(commentEntity.getUserId());
            userIds.add(commentEntity.getCommentedId());
        });
        Map<Long, List<ReplyVo>> replyVoMap = replyService.listByCommentIdsWithSizeOf(commentIds, 2);

        Map<Long, UserVo> userVoMap = userService.listByIds(userIds).stream()
                .map(userEntity -> BeanUtil.copyProperties(userEntity, new UserVo()))
                .collect(Collectors.toMap(UserVo::getUserId, Function.identity()));

        return commentEntities.stream().map(commentEntity -> {
            CommentVo commentVo = BeanUtil.copyProperties(commentEntity, new CommentVo());
            commentVo.setTopicId(commentEntity.getTopicId());
            commentVo.setUser(userVoMap.get(commentEntity.getUserId()));
            commentVo.setCommentedUser(userVoMap.get(commentEntity.getCommentedId()));
            commentVo.setRepliesByLikes(replyVoMap.get(commentEntity.getCommentId()));
            return commentVo;
        }).toList();
    }

}