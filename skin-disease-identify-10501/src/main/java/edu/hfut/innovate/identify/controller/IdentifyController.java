package edu.hfut.innovate.identify.controller;

import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.identify.service.IdentifyResService;
import edu.hfut.innovate.identify.service.IdentifyService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Chowhound
 * @since : 2024/2/20 - 21:50
 */
@RestController
public class IdentifyController {
    private IdentifyResService resService;
    private IdentifyService identifyService;
    public IdentifyController(IdentifyService identifyService, IdentifyResService resService) {
        this.identifyService = identifyService;
        this.resService = resService;
    }

    @PostMapping("/{identify_id}")
    public R getCommentById(@PathVariable("identify_id") Long identifyId,
                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token){



        return R.ok();
    }




}
