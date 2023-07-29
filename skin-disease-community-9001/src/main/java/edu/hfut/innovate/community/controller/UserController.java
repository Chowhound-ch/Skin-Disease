package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.CommunityTypeUtil;
import edu.hfut.innovate.common.util.ItemSize;
import edu.hfut.innovate.common.util.TokenManager;
import edu.hfut.innovate.common.vo.community.CommentVo;
import edu.hfut.innovate.common.vo.community.UserVo;
import edu.hfut.innovate.common.vo.user.LoginInfo;
import edu.hfut.innovate.community.entity.CollectionRecord;
import edu.hfut.innovate.community.entity.CommentEntity;
import edu.hfut.innovate.community.entity.LikeRecord;
import edu.hfut.innovate.community.entity.UserEntity;
import edu.hfut.innovate.community.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author Chowhound
 */
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
//    @Autowired
//    private CommentService commentService;
//    @Autowired
//    private TopicTagService topicTagService;
//    @Autowired
//    private ReplyService replyService;
    @Autowired
    private LikeRecordService likeRecordService;
    @Autowired
    private CollectionRecordService collectionRecordService;
    @Autowired
    private TokenManager tokenManager;

    /**
     * 小程序中根据手机号登录
     */
    @PostMapping("/login/phone")
    @ApiOperation("登录")
    public R login(LoginInfo info){
        UserEntity userEntity = userService.login(info.getUsername(), info.getPhone());
        if (userEntity == null){
            return R.error("用户名或密码错误");
        }
        UserVo userVo = BeanUtil.copyProperties(userEntity, new UserVo());

        String token = tokenManager.createToken(userVo);

        return R.ok(Map.entry("token", token));
    }

//    @ApiOperation(value = "用户详情")
//    @GetMapping("/{user_id}")
//    public R getCommentById(@PathVariable("user_id") Long userId){
//        UserEntity userEntity = userService.getById(userId);
//        if (userEntity == null){
//            return R.error(404,  "用户不存在");
//        }
//        return R.ok(BeanUtil.copyProperties(userEntity, new UserVo()));
//    }
//    @ApiOperation(value = "点赞")
//    @PostMapping("/like")
//    public R like(
//            @ApiParam(value = "", required = true)
//            @RequestBody LikeRecord likeRecord){
//        UserEntity userEntity = userService.getById(userId);
//        if (userEntity == null){
//            return R.error(404,  "用户不存在");
//        }
//        LikeRecord likeRecord = new LikeRecord();
//        if (type.equals(CommunityTypeUtil.COMMENT)){
//            likeRecordService
//
//        }else if (type.equals(CommunityTypeUtil.TOPIC)){
//        }else if (type.equals(CommunityTypeUtil.REPLY)){
//        }else {
//            return R.error(403, "点赞类型错误");
//        }
//
//
//
//
//        return R.ok();
//    }
    /**
     * 列表
     */
    @GetMapping("/list")
//    @RequiresPermissions("community:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils<UserEntity> page = userService.queryPage(params);

        return R.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
//    @RequiresPermissions("community:user:info")
    public R info(@PathVariable("userId") Long userId){
		UserEntity user = userService.getById(userId);

        return R.ok(user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("community:user:save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("community:user:update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("community:user:delete")
    public R delete(@RequestBody Long[] userIds){
		userService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
