package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.config.mvc.TokenUser;
import edu.hfut.innovate.common.config.mvc.TokenValue;
import edu.hfut.innovate.common.domain.dto.community.UserDto;
import edu.hfut.innovate.common.domain.dto.community.WeChatUserInfoDto;
import edu.hfut.innovate.common.domain.entity.LoginInfo;
import edu.hfut.innovate.common.domain.entity.UserAuth;
import edu.hfut.innovate.common.domain.vo.community.UserVo;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.MiniProgramUtil;
import edu.hfut.innovate.common.util.TokenManager;
import edu.hfut.innovate.community.entity.UserEntity;
import edu.hfut.innovate.community.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;




/**
 * <p> 前端若存储有token，则请求/community/user/check/wechat, 若token有效则后端会刷新token，并将新token返回给前端
 *     若token无效则响应的code会是403</p>
 *
 * <p> 前端若没有token，则前端调用{@code wx.login}，以wx.login返回的code为参数请求/community/user/login/wechat,
 *     若用户已经授权过信息则后端会直接返回token，否则响应的code会是403，即未授权注册过</p>
 *
 *  <p> 注册则先再前端调用{@code wx.getUserProfile}获得用户授权的信息info，并将wx.login获得的code作为info的一个属性，
 *      属性名为jsCode，即{@code info.jsCode = code}，在一info为参数，请求/community/user/register/wechat，
 *      注册成功则返回token</p>
 *
 * @author Chowhound
 */
@Slf4j
@Api(tags = "用户相关接口")
@Import(MiniProgramUtil.class)
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private MiniProgramUtil miniProgramUtil;

    // region 微信小程序登录
    @PostMapping("/login/wechat")
    @ApiOperation("微信小程序登录")
    public R wechatLogin(@RequestBody LoginInfo info){

        String openId = miniProgramUtil.getOpenId(info.getJsCode());
        UserEntity userEntity = userService.wechatLogin(openId);
        if (userEntity == null){
            log.info("用户{}不存在, 需要授权注册", openId);
            return R.error(HttpStatus.FORBIDDEN.value(), "用户不存在, 需要授权注册");
        }
        UserVo userVo = BeanUtil.copyProperties(userEntity, new UserVo());

        return R.ok(tokenManager.createDoubleToken(userVo));
    }

    @PostMapping("/login/refresh")
    @ApiOperation("微信小程序登录")
    public R refreshToken(@TokenValue String token){
        UserAuth userAuth = tokenManager.getUserAuth(token, TokenManager.REFRESH_TOKEN);
        if (userAuth == null) {
            log.info("无效的refresh_token");
            return R.error(HttpStatus.FORBIDDEN.value(), "无效token");
        }
        UserVo userVo = BeanUtil.copyProperties(userAuth, new UserVo());
        String accessToken = tokenManager.refreshAccessToken(userVo, token);

        return R.ok().putData(Map.of(TokenManager.ACCESS_TOKEN, accessToken));
    }



//    @GetMapping("/check/wechat")
//    @ApiOperation("刷新token存在时间")
//    public R wechatLoginCheck(@RequestHeader("Authorization") String token){
//        UserAuth user = tokenManager.getUserVoFromTokenWithBearer(token);
//        tokenManager.deleteTokenWithBearer(token);
//
//        return R.ok(Map.of("token", tokenManager.createToken(userService.getVoById(user.getUserId())), "user", user));
//    }
    @PostMapping("/register/wechat")
    @ApiOperation("微信小程序授权")
    public R wechatEmpower(@RequestBody WeChatUserInfoDto info){
        String openId = miniProgramUtil.getOpenId(info.getJsCode());
        if (openId == null){
            log.error("jsCode无效");
            return R.error(HttpStatus.FORBIDDEN.value(), "未传入jsCode或jsCode无效");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setAvatar(info.getAvatarUrl());
        userEntity.setNickName(info.getNickName());
        userEntity.setSex(2); // 2: 未知
        userEntity.setOpenid(openId);

        UserVo userVo = userService.register(userEntity);

        return R.ok(tokenManager.createDoubleToken(userVo));
    }

    /**
     * @author : Chowhound
     * @since : 2023/08/03 - 20:17
     */
    @Deprecated
    @PostMapping("/login/phone")
    @ApiOperation("登录")
    public R login(@RequestBody LoginInfo info){
        UserEntity userEntity = userService.login(info.getUsername(), info.getPhone());
        if (userEntity == null){
            return R.error(HttpStatus.FORBIDDEN.value(), "用户名或密码错误");
        }
        UserVo userVo = BeanUtil.copyProperties(userEntity, new UserVo());

        String token = tokenManager.createToken(userVo);

        return R.ok(Map.of("token", token, "user", userVo));
    }

    // endregion

    @ApiOperation(value = "用户详情")
    @GetMapping("/{user_id}")
    public R getCommentById(@PathVariable("user_id") Long userId){
        UserVo userVo = userService.getVoById(userId);
        if (userVo == null){
            return R.error(404,  "用户不存在");
        }
        return R.ok(userVo);
    }
    @ApiOperation(value = "个人信息")
    @GetMapping("/")
    public R getCommentById(@TokenUser UserAuth userAu){
        ;
        if (userAu == null){
            return R.error(404,  "用户不存在");
        }

        return R.ok(userService.getVoById(userAu.getUserId()));
    }
    @ApiOperation(value = "更新用户信息")
    @PostMapping("/update")
    public R updateUserInfo(@RequestBody UserDto userDto,
                            @TokenUser UserAuth userVo){
        UserEntity userEntity = BeanUtil.copyProperties(userDto, new UserEntity());
        userEntity.setUserId(userVo.getUserId());
        userEntity.setLikes(null);
        userService.updateById(userEntity);
        return R.ok();
    }

}
