package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.community.service.ForwardRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public R getForwardRecordByUserId(@RequestParam("user_id") Long userId,
                                      @RequestParam("topic_id") Long topicId){

        return R.ok(forwardRecordService.getForwardRecordByUserId(topicId, userId));
    }

    @GetMapping("/{forward_key}")
    public R saveForwardRecord(@PathVariable("forward_key") String forwardKey){

        return R.ok(forwardRecordService.getForwardRecordByKey(forwardKey));
    }




}
