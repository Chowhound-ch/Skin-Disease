package edu.hfut.innovate.gateway.security.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author : Chowhound
 * @since : 2023/7/26 - 18:18
 */
@FeignClient("skin-disease-community")
public interface UserController {

}
