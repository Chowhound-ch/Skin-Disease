package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import edu.hfut.innovate.community.dao.UserDao;
import edu.hfut.innovate.community.entity.UserEntity;
import edu.hfut.innovate.community.service.UserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserEntity login(String username, String password) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getUsername, username)
                .eq(UserEntity::getPassword, password);

        return this.getOne(queryWrapper);
    }

}