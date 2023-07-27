package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.Query;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.vo.community.ReplyVo;
import edu.hfut.innovate.common.vo.community.UserVo;
import edu.hfut.innovate.community.dao.ReplyDao;
import edu.hfut.innovate.community.entity.ReplyEntity;
import edu.hfut.innovate.community.service.ReplyService;
import edu.hfut.innovate.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service("replyService")
public class ReplyServiceImpl extends ServiceImpl<ReplyDao, ReplyEntity> implements ReplyService {
    @Autowired
    private UserService userService;

    @Override
    public PageUtils<ReplyEntity> queryPage(Map<String, Object> params) {
        IPage<ReplyEntity> page = this.page(
                new Query<ReplyEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils<>(page);
    }

    @Override
    public Map<Long, List<ReplyVo>> listByCommentIdsWithSizeOf(Collection<Long> idSet, int size) {
        List<ReplyEntity> replyEntities = list(
                new LambdaQueryWrapper<ReplyEntity>().in(ReplyEntity::getCommentId, idSet));
        // 根据评论id进行分组
        Map<Long, List<ReplyEntity>> groupMap = replyEntities.stream()
                .collect(Collectors.groupingBy(ReplyEntity::getCommentId));
        // 获取所有涉及的userId
        Set<Long> userIdSet = new HashSet<>(idSet.size() * size );


        List<Map.Entry<Long, List<ReplyEntity>>> entryList = groupMap.entrySet().stream().map(entry -> {
            List<ReplyEntity> values = entry.getValue().stream()
                    .sorted((o1, o2) -> o2.getLikes() - o1.getLikes())
                    .limit(size).toList();
            // 获取所有涉及的userId
            values.forEach(replyEntity -> {
                userIdSet.add(replyEntity.getUserId());
                userIdSet.add(replyEntity.getReplyId());
            });
            // 取likes最多的前size个
            return Map.entry(entry.getKey(), values);
        }).toList();
        // 根据userId获取userVo
        Map<Long, UserVo> userMap = userService.listByIds(userIdSet).stream()
                .map(userEntity -> BeanUtil.copyProperties(userEntity, new UserVo()))
                .collect(Collectors.toMap(UserVo::getUserId, userVo -> userVo));

        // 将entry.value转化为ReplyVo
        return entryList.stream().map(entry -> {
            List<ReplyVo> replyVos = entry.getValue().stream()
                    .map(replyEntity -> {
                        ReplyVo replyVo = BeanUtil.copyProperties(replyEntity, new ReplyVo());
                        replyVo.setUser(userMap.get(replyEntity.getUserId()));
                        replyVo.setReplied(userMap.get(replyVo.getReplyId()));

                        return replyVo;
                    }).toList();
            return Map.entry(entry.getKey(), replyVos);
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}