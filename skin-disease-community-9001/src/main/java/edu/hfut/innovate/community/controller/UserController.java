package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.domain.entity.LoginInfo;
import edu.hfut.innovate.common.domain.vo.community.UserVo;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.TokenManager;
import edu.hfut.innovate.community.entity.UserEntity;
import edu.hfut.innovate.community.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private TokenManager tokenManager;

    /**
     * 小程序中根据手机号登录
     */
    @PostMapping("/login/phone")
    @ApiOperation("登录")
    public R login(@RequestBody LoginInfo info){
        UserEntity userEntity = userService.login(info.getUsername(), info.getPhone());
        if (userEntity == null){
            return R.error(HttpStatus.FORBIDDEN.value(), "用户名或密码错误");
        }
        UserVo userVo = BeanUtil.copyProperties(userEntity, new UserVo());

        String token = tokenManager.createToken(userVo);

        return R.ok(Map.entry("token", token));
    }

    @ApiOperation(value = "用户详情")
    @GetMapping("/{user_id}")
    public R getCommentById(@PathVariable("user_id") Long userId){
        UserEntity userEntity = userService.getById(userId);
        if (userEntity == null){
            return R.error(404,  "用户不存在");
        }
        return R.ok(BeanUtil.copyProperties(userEntity, new UserVo()));
    }

}
