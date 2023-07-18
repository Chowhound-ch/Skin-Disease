package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.community.entity.ReplyEntity;
import edu.hfut.innovate.community.service.ReplyService;
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
@RequestMapping("reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("community:reply:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = replyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{replyId}")
//    @RequiresPermissions("community:reply:info")
    public R info(@PathVariable("replyId") Long replyId){
		ReplyEntity reply = replyService.getById(replyId);

        return R.ok().put("reply", reply);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("community:reply:save")
    public R save(@RequestBody ReplyEntity reply){
		replyService.save(reply);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("community:reply:update")
    public R update(@RequestBody ReplyEntity reply){
		replyService.updateById(reply);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("community:reply:delete")
    public R delete(@RequestBody Long[] replyIds){
		replyService.removeByIds(Arrays.asList(replyIds));

        return R.ok();
    }

}
