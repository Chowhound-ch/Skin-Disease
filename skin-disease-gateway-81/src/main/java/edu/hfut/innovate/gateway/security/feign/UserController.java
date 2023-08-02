package edu.hfut.innovate.gateway.security.feign;

import edu.hfut.innovate.common.renren.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

/**
 * @author : Chowhound
 * @since : 2023/7/26 - 18:18
 */
@Controller
@ReactiveFeignClient(value = "skin-disease-community", path = "/user")
public interface UserController {
    @PostMapping("/login/phone")
    Mono<R> login(@RequestParam("username")String username, @RequestParam("phone")String phone);

}
