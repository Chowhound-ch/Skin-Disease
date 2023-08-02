package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.domain.dto.community.CommentDto;
import edu.hfut.innovate.common.domain.entity.UserAuth;
import edu.hfut.innovate.common.domain.vo.community.CommentVo;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.TokenManager;
import edu.hfut.innovate.community.entity.CommentEntity;
import edu.hfut.innovate.community.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private TokenManager tokenManager;

    @ApiOperation(value = "根据评论Id查询评论", notes = "包括评论的回复(全部), 以及评论的用户信息")
    @GetMapping("/{comment_id}")
    public R getCommentById(@PathVariable("comment_id") Long commentId,
                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        UserAuth auth = tokenManager.getUserFromTokenWithBearer(token);

        CommentVo commentVo = commentService.getCommentById(commentId, auth.getUserId());

        return R.ok(commentVo);
    }

    /**
     * 发布评论
     */
    @PostMapping("/save")
    public R save(@RequestBody CommentDto comment){
        CommentEntity commentEntity = BeanUtil.copyProperties(comment, new CommentEntity());
        commentService.save(commentEntity);

        return R.ok();
    }

    /**
     * 删除评论
     * TODO 仅可删除自己的评论，后续应该进行token验证用户身份
     */
    @RequestMapping("/delete/{comment_id}")
    public R delete(@PathVariable("comment_id") Long commentId,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        UserAuth auth = tokenManager.getUserFromTokenWithBearer(token);

        CommentEntity commentEntity = commentService.getById(commentId);
        // 如果有管理员角色


        if (commentEntity == null){
            return R.error(HttpStatus.NOT_FOUND.value(), "评论不存在");
        } else if ( !commentEntity.getUserId().equals(auth.getUserId())){
            return R.error(HttpStatus.FORBIDDEN.value(), "无权限删除");
        }

        commentService.removeByIdWithReply(commentId);

        return R.ok();
    }

}
