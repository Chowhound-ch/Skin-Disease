package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.vo.community.CommentVo;
import edu.hfut.innovate.common.vo.community.UserVo;
import edu.hfut.innovate.community.entity.CommentEntity;
import edu.hfut.innovate.community.entity.UserEntity;
import edu.hfut.innovate.community.service.CommentService;
import edu.hfut.innovate.community.service.ReplyService;
import edu.hfut.innovate.community.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



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
    @Autowired
    private ReplyService replyService;

    @Autowired
    private UserService userService;

    @GetMapping("/{comment_id}")
    public R getCommentById(@PathVariable("comment_id") Long commentId){
        CommentEntity commentEntity = commentService.getById(commentId);
        CommentVo commentVo = BeanUtil.copyProperties(commentEntity, new CommentVo());
        // 设置UserVo
        UserEntity userEntity = userService.getById(commentEntity.getUserId());
        commentVo.setUser(BeanUtil.copyProperties(userEntity, new UserVo()));

        // 设置ReplyVo
        commentVo.setReplies(replyService.listByCommentId(commentId));

        return R.ok(commentVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @Transactional
//    @RequiresPermissions("community:comment:save")
    public R save(@RequestBody CommentEntity comment){
		commentService.save(comment);
        if (comment.getCreateTime() == null) {
            throw new RuntimeException("创建时间不能为空");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("community:comment:update")
    public R update(@RequestBody CommentEntity comment){
		commentService.updateById(comment);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("community:comment:delete")
    public R delete(@RequestBody Long[] commentIds){
		commentService.removeByIds(Arrays.asList(commentIds));

        return R.ok();
    }

}
