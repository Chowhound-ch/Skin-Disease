package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.common.vo.community.CommentVo;
import edu.hfut.innovate.common.vo.community.TopicVo;
import edu.hfut.innovate.common.vo.community.UserVo;
import edu.hfut.innovate.community.entity.TopicEntity;
import edu.hfut.innovate.community.entity.UserEntity;
import edu.hfut.innovate.community.service.CommentService;
import edu.hfut.innovate.community.service.TopicService;
import edu.hfut.innovate.community.service.TopicTagService;
import edu.hfut.innovate.community.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 
 *
 * @author Chowhound
 */
@Api(tags = "话题相关接口")
@RestController
@RequestMapping("topic")
public class TopicController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private TopicTagService topicTagService;

    /**
     * 列表
     */
    @RequestMapping("/page")
//    @RequiresPermissions("community:topic:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils<TopicEntity> page = topicService.queryPage(params);
        // 获取所有的TopicId
        Collection<Long> topicIds = CollectionUtil.getCollection(page.getList(), TopicEntity::getTopicId);
        // 根据TopicId分组
        Map<Long, List<CommentVo>> commentVoMap = commentService.listByTopicIds(topicIds)
                .stream().collect(Collectors.groupingBy(CommentVo::getTopicId));

        List<UserEntity> userEntities = userService.listByIds(
                // 获取所有的UserId
                CollectionUtil.getCollection(page.getList(), TopicEntity::getUserId));
        Map<Long, UserEntity> userEntityMap = CollectionUtil.getMap(userEntities, UserEntity::getUserId);
        // 将UserEntity转换为UserVo
        Map<Long, UserVo> userVoMap = userEntityMap.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), BeanUtil.copyProperties(entry.getValue(), new UserVo())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Long, List<String>> tagMap = topicTagService.mapByTopicIds(topicIds);

        List<TopicVo> list = page.getList().stream().map(topicEntity -> {
            TopicVo topicVo = BeanUtil.copyProperties(topicEntity, new TopicVo());
            topicVo.setComments(commentVoMap.get(topicEntity.getTopicId()));
            topicVo.setUser(userVoMap.get(topicEntity.getUserId()));
            topicVo.setTags(tagMap.get(topicEntity.getTopicId()));

            return topicVo;
        }).toList();

        return R.ok(new PageUtils<>(list, page.getTotalCount(), page.getPageSize(), page.getCurrPage()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{topicId}")
//    @RequiresPermissions("community:topic:info")
    public R info(@PathVariable("topicId") Long topicId){
		TopicEntity topic = topicService.getById(topicId);

        return R.ok(topic);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("community:topic:save")
    public R save(@RequestBody TopicEntity topic){
		topicService.save(topic);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("community:topic:update")
    public R update(@RequestBody TopicEntity topic){
		topicService.updateById(topic);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("community:topic:delete")
    public R delete(@RequestBody Long[] topicIds){
		topicService.removeByIds(Arrays.asList(topicIds));

        return R.ok();
    }

}
