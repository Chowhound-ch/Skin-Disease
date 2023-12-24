package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.domain.vo.community.UserVo;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.community.dao.UserMapper;
import edu.hfut.innovate.community.entity.UserEntity;
import edu.hfut.innovate.community.exception.UserHasRegistered;
import edu.hfut.innovate.community.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public UserEntity login(String username, String phone) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getUsername, username)
                .eq(UserEntity::getPhone, phone);

        return this.getOne(queryWrapper);
    }

    @Override
    public UserEntity wechatLogin(String code) {
        return this.getOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getOpenid, code));
    }

    @Override
    public Map<Long, UserVo> mapByIds(Collection<Long> ids) {

        return CollectionUtil.getMap(this.listByIds(ids).stream()
                .map(userEntity -> BeanUtil.copyProperties(userEntity, new UserVo()))
                .toList(), UserVo::getUserId);
    }

    @Override
    public void register(UserEntity userEntity) {
        if (this.getOne(new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getOpenid, userEntity.getOpenid())) != null) {
            throw new UserHasRegistered("该用户已经注册过了");
        }
        this.save(userEntity);
    }

    @Override
    public UserVo getVoById(Long id) {
        return BeanUtil.copyProperties(getById(id), new UserVo());
    }

}