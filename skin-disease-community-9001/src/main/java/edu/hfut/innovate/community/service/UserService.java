package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.community.entity.UserEntity;

import java.util.Map;

/**
 * 
 *
 * @author Chowhound
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UserEntity login(String username, String password);
}

