package edu.hfut.innovate.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.domain.entity.Auth;
import edu.hfut.innovate.common.domain.enums.RolesEnum;
import edu.hfut.innovate.common.mapper.AuthMapper;
import edu.hfut.innovate.common.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, Auth>
    implements AuthService{
    private static final RolesEnum DEFAULT_ROLE_ENUM = RolesEnum.USER;

    @Override
    public List<Auth> listByUserId(Long userId) {
        List<Auth> authList = list(new LambdaQueryWrapper<Auth>().eq(Auth::getUserId, userId));
        if (authList.isEmpty()) {
            Auth auth = new Auth(null, userId, DEFAULT_ROLE_ENUM);
            this.save(auth);
            authList.add(auth);
        }

        return authList;
    }
}




