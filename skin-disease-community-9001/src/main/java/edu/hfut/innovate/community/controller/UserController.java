package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.vo.community.UserVo;
import edu.hfut.innovate.community.entity.UserEntity;
import edu.hfut.innovate.community.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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


    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    public R login(String username, String password, String phone){
        UserEntity userEntity = userService.login(username, password, phone);
        if (userEntity == null){
            return R.error("用户名或密码错误");
        }

        UserVo userVo = BeanUtil.copyProperties(userEntity, new UserVo());
        return R.ok(userVo);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("community:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

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
