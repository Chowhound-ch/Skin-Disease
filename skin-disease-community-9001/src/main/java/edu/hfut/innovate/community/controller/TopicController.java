package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.community.entity.TopicEntity;
import edu.hfut.innovate.community.service.TopicService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("community:topic:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = topicService.queryPage(params);

        return R.ok(page);
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
