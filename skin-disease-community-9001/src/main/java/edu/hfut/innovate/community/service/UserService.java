package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.community.entity.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Chowhound
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UserEntity login(String username, String phone);

    /**
     * @author : Chowhound
     * @since : 2023/07/27 - 20:04
     * @param ids : 用户id集合
     * @return : java.util.Map<java.lang.Long, java.util.List<edu.hfut.innovate.community.entity.UserEntity>>
     *     key: 用户id
     *     value: 用户信息
     */
    Map<Long, UserEntity> mapByIds(Collection<Long> ids);
}

