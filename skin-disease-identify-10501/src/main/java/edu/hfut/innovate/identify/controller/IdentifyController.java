package edu.hfut.innovate.identify.controller;

import edu.hfut.innovate.common.domain.dto.identify.IdentifyDto;
import edu.hfut.innovate.common.domain.entity.UserAuth;
import edu.hfut.innovate.common.domain.vo.identify.IdentifyVo;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.identify.service.IdentifyResService;
import edu.hfut.innovate.identify.service.IdentifyService;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Chowhound
 * @since : 2024/2/20 - 21:50
 */
@RequestMapping("identify")
@RestController
public class IdentifyController {
    private final IdentifyResService resService;
    private final IdentifyService identifyService;
    public IdentifyController(IdentifyService identifyService, IdentifyResService resService) {
        this.identifyService = identifyService;
        this.resService = resService;
    }

    @GetMapping("/{identify_id}")
    public R getIdentifyById(@PathVariable("identify_id") Long identifyId) throws NullPointerException{
        IdentifyVo identify = identifyService.getIdentifyById(identifyId);
        if (identify == null) {
            throw new NullPointerException("Identify not found");
        }

        return R.ok(identify);
    }

    @PostMapping("/")
    public R postIdentifyByUserId(@RequestBody IdentifyDto identifyDto, UserAuth auth) throws NullPointerException{
        identifyDto.setUserId(auth.getUserId());
        IdentifyVo identifyVo = identifyService.saveIdentify(identifyDto);
        if (identifyVo == null) {
            throw new NullPointerException("保存失败");
        }
        // TODO 保存成功后，调用识别服务


        return R.ok();
    }


}
