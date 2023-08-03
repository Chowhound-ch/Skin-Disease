package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.domain.vo.community.UserVo;
import edu.hfut.innovate.community.entity.UserEntity;

import java.util.Collection;
import java.util.Map;

/**
 * 
 *
 * @author Chowhound
 */
public interface UserService extends IService<UserEntity> {

    UserEntity login(String username, String phone);

    UserEntity wechatLogin(String code);

    /**
     * @author : Chowhound
     * @since : 2023/07/27 - 20:04
     * @param ids : 用户id集合
     * @return : java.util.Map<java.lang.Long, java.util.List<edu.hfut.innovate.community.entity.UserEntity>>
     *     key: 用户id
     *     value: 用户信息
     */
    Map<Long, UserVo> mapByIds(Collection<Long> ids);

    void register(UserEntity userEntity);
}

