package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.vo.community.TopicVo;
import edu.hfut.innovate.community.entity.TopicEntity;
import edu.hfut.innovate.community.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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

    /**
     * 分页查询话题
     */
    @ApiOperation("分页查询话题")
    @GetMapping("/page/{user_id}")
//    @RequiresPermissions("community:topic:list")
    public R list(
            @ApiParam("分页查询参数")
            @RequestParam Map<String, Object> params,
            @PathVariable("user_id") Long userId){

        return R.ok(topicService.queryPageByUserId(params, userId));
    }

    @ApiOperation("根据话题id查询话题(详细信息)")
    @GetMapping("/{topicId}/{userId}")
    public R getTopic(@PathVariable("topicId") Long topicId, @PathVariable("userId") Long userId) {
        TopicVo topicVo = topicService.getTopicById(topicId, userId);
        if (topicVo == null) {
            return R.error("话题不存在");
        }

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

		topicService.updateById(topicEntity);

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
