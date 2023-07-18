package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.community.entity.TopicTagEntity;
import edu.hfut.innovate.community.service.TopicTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author Chowhound
 */
@RestController
@RequestMapping("topictag")
public class TopicTagController {
    @Autowired
    private TopicTagService topicTagService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("community:topictag:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = topicTagService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{tagId}")
//    @RequiresPermissions("community:topictag:info")
    public R info(@PathVariable("tagId") Long tagId){
		TopicTagEntity topicTag = topicTagService.getById(tagId);

        return R.ok().put("topicTag", topicTag);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("community:topictag:save")
    public R save(@RequestBody TopicTagEntity topicTag){
		topicTagService.save(topicTag);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("community:topictag:update")
    public R update(@RequestBody TopicTagEntity topicTag){
		topicTagService.updateById(topicTag);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("community:topictag:delete")
    public R delete(@RequestBody Long[] tagIds){
		topicTagService.removeByIds(Arrays.asList(tagIds));

        return R.ok();
    }

}
