package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.vo.community.CommentVo;
import edu.hfut.innovate.community.entity.CommentEntity;
import edu.hfut.innovate.community.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * 
 *
 * @author Chowhound
 */
@Api(tags = "评论相关接口")
@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "根据评论Id查询评论", notes = "包括评论的回复(全部), 以及评论的用户信息")
    @GetMapping("/{comment_id}/{user_id}")
    public R getCommentById(@PathVariable("comment_id") Long commentId, @PathVariable("user_id") Long userId){

        CommentVo commentVo = commentService.getCommentById(commentId, userId);

        return R.ok(commentVo);
    }

    /**
     * 发布评论
     */
    @PostMapping("/save")
//    @RequiresPermissions("community:comment:save")
    public R save(@RequestBody CommentEntity comment){
		commentService.save(comment);

        return R.ok();
    }

    /**
     * 删除评论
     * TODO 仅可删除自己的评论，后续应该进行token验证用户身份
     */
    @RequestMapping("/delete/{comment_id}")
//    @RequiresPermissions("community:comment:delete")
    public R delete(@PathVariable("comment_id") Long commentId){
		commentService.removeByIdWithReply(commentId);

        return R.ok();
    }

}
