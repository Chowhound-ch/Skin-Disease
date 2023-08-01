package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.dto.community.ForwardRecordDto;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.community.service.ForwardRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Chowhound
 * @since : 2023/8/1 - 17:54
 */
@RequestMapping("/forward")
@RestController
public class ForwardRecordController {

    @Autowired
    private ForwardRecordService forwardRecordService;

    @GetMapping("/")
    public R getForwardRecordByUserId(
            @RequestBody ForwardRecordDto forwardRecordDto){

        return R.ok(forwardRecordService.getForwardRecordByUserId(
                forwardRecordDto.getTopicId(), forwardRecordDto.getUserId()));
    }




}
