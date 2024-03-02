package edu.hfut.innovate.identify.controller;

import edu.hfut.innovate.common.domain.dto.identify.IdentifyDto;
import edu.hfut.innovate.common.domain.entity.UserAuth;
import edu.hfut.innovate.common.domain.vo.identify.IdentifyResVo;
import edu.hfut.innovate.common.domain.vo.identify.IdentifyVo;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.identify.entity.IdentifyRes;
import edu.hfut.innovate.identify.service.IdentifyResService;
import edu.hfut.innovate.identify.service.IdentifyService;
import edu.hfut.innovate.identify.util.IdentifierHelper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Chowhound
 * @since : 2024/2/20 - 21:50
 */
@RequestMapping("identify")
@RestController
public class IdentifyController {
    private final IdentifierHelper identifierHelper;
    private final IdentifyService identifyService;
    private final IdentifyResService identifyResService;
    public IdentifyController(IdentifyService identifyService, IdentifierHelper identifierHelper, IdentifyResService identifyResService) {
        this.identifyService = identifyService;
        this.identifierHelper = identifierHelper;
        this.identifyResService = identifyResService;
    }

    @GetMapping("/{identify_id}")
    public R getIdentifyById(@PathVariable("identify_id") Long identifyId) throws NullPointerException{
        IdentifyVo identify = identifyService.getIdentifyById(identifyId);
        if (identify == null) {
            throw new NullPointerException("Identify not found");
        }

        return R.ok(identify);
    }

    @Transactional
    @PostMapping("/")
    public R postIdentifyByUserId(@RequestBody IdentifyDto identifyDto, UserAuth auth) throws NullPointerException{
        identifyDto.setUserId(auth.getUserId());
        IdentifyVo identifyVo = identifyService.saveIdentify(identifyDto);
        if (identifyVo == null) {
            throw new NullPointerException("保存失败");
        }
        IdentifyRes identifyRes = identifierHelper.doGetIdentify(identifyVo.getImgUrl());
        identifyRes.setResId(identifyVo.getIdentifyId());
        identifyResService.save(identifyRes);
        identifyVo.setRes(BeanUtil.copyProperties(identifyRes, new IdentifyResVo()));

        return R.ok(identifyVo);
    }


}
