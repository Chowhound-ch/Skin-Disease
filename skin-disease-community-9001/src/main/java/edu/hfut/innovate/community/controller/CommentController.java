package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.vo.community.CommentVo;
import edu.hfut.innovate.community.entity.CommentEntity;
import edu.hfut.innovate.community.service.CommentService;
import edu.hfut.innovate.community.service.UserService;
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
@Api(tags = "评论相关接口")
@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @GetMapping("/{comment_id}")
    public R getCommentById(@PathVariable("comment_id") Long commentId){
        CommentEntity commentEntity = commentService.getById(commentId);

        CommentVo commentVo = BeanUtil.copyProperties(commentEntity, new CommentVo());

        return R.ok(commentEntity);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("community:comment:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = commentService.queryPage(params);

        return R.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{commentId}")
//    @RequiresPermissions("community:comment:info")
    public R info(@PathVariable("commentId") Long commentId){
		CommentEntity comment = commentService.getById(commentId);

        return R.ok(comment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("community:comment:save")
    public R save(@RequestBody CommentEntity comment){
		commentService.save(comment);

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
