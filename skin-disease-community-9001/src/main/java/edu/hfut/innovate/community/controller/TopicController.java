package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.common.util.ItemSize;
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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
     * 分页查询话题
     */
    @ApiOperation("分页查询话题")
    @GetMapping("/page")
//    @RequiresPermissions("community:topic:list")
    public R list(
            @ApiParam("分页查询参数")
            @RequestParam Map<String, Object> params){
        PageUtils<TopicEntity> page = topicService.queryPage(params);
        // 获取所有的TopicId
        Collection<Long> topicIds = CollectionUtil.getCollection(page.getList(), TopicEntity::getTopicId);
        // 根据TopicId分组
        Map<Long, List<CommentVo>> commentVoMap = commentService.mapByTopicIds(topicIds, 2, 0);

        Map<Long, UserEntity> userEntityMap = userService.mapByIds(
                // 获取所有的UserId
                CollectionUtil.getCollection(page.getList(), TopicEntity::getUserId));
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

    @ApiOperation("根据话题id查询话题(详细信息)")
    @GetMapping("/{topicId}")
    public R getTopic(@PathVariable("topicId") Long topicId) {
        TopicEntity topicEntity = topicService.getById(topicId);
        if (topicEntity == null) {
            return R.error("话题不存在");
        }
        TopicVo topicVo = BeanUtil.copyProperties(topicEntity, new TopicVo());
        topicVo.setTags(topicTagService.getByTopicId(topicId));
        topicVo.setUser(BeanUtil.copyProperties(userService.getById(topicEntity.getUserId()), new UserVo()));
        topicVo.setComments(commentService.getByTopicId(topicId, ItemSize.ALL, ItemSize.PARTS_BY_LIKES));

        return R.ok(topicVo);
    }

    /**
     * 保存
     */
    @ApiOperation("新增话题")
    @PostMapping("/save")
//    @RequiresPermissions("community:topic:save")
    public R save(@RequestBody TopicEntity topic){
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setContent(topic.getContent());
        topicEntity.setTitle(topic.getTitle());
        topicEntity.setUserId(topic.getUserId());

		topicService.save(topicEntity);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改话题 content 和 title")
    @PostMapping("/update")
//    @RequiresPermissions("community:topic:update")
    public R update(@RequestBody TopicEntity topic){
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicId(topic.getTopicId());
        topicEntity.setContent(topic.getContent());
        topicEntity.setTitle(topic.getTitle());

		topicService.updateById(topic);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除话题")
    @Transactional
    @PostMapping("/delete/{topicId}")
//    @RequiresPermissions("community:topic:delete")
    public R delete(@PathVariable Long topicId){
//		topicService.removeByIds(Arrays.asList(topicId));
        topicService.removeTopicById(topicId);
        return R.ok();
    }

}
