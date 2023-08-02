package edu.hfut.innovate.common.service;

import edu.hfut.innovate.common.domain.entity.Auth;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AuthService extends IService<Auth> {

    List<Auth> listByUserId(Long userId);
}
