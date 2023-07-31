package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.community.entity.ReplyEntity;
import edu.hfut.innovate.community.service.ReplyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 
 *
 * @author Chowhound
 */
@Api(tags = "回复相关接口")
@RestController
@RequestMapping("reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

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
     * 删除
     * TODO 仅可删除自己的评论，后续应该进行token验证用户身份
     */
    @RequestMapping("/delete/{reply_id}")
//    @RequiresPermissions("community:reply:delete")
    public R delete(@PathVariable("reply_id") String replyId){
		replyService.removeById(replyId);

        return R.ok();
    }

}
