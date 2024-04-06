package edu.hfut.innovate.identify.controller;

import edu.hfut.innovate.common.config.mvc.TokenUser;
import edu.hfut.innovate.common.domain.dto.identify.IdentifyDto;
import edu.hfut.innovate.common.domain.entity.UserAuth;
import edu.hfut.innovate.common.domain.vo.community.UserVo;
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

import java.util.List;

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
    @GetMapping("/")
    public R getIdentifyByToken(@TokenUser UserAuth userAuth) throws NullPointerException{
        List<IdentifyVo> identify = identifyService.getIdentifyByUserId(userAuth.getUserId());
        if (identify == null) {
            throw new NullPointerException("Identify not found");
        }

        return R.ok(identify);
    }

    @Transactional
    @PostMapping("/")
    public R postIdentifyByUserId(@RequestBody IdentifyDto identifyDto, @TokenUser UserAuth auth) throws NullPointerException{
        identifyDto.setUserId(auth.getUserId());
        IdentifyVo identifyVo = identifyService.getByImg(identifyDto.getImgUrl(), auth.getUserId());
        if (identifyVo != null) {
            return R.ok(identifyVo);
        }
        IdentifyRes identifyRes = identifierHelper.doGetIdentify(identifyDto.getImgUrl());
        IdentifyResVo resVo = BeanUtil.copyProperties(identifyRes, new IdentifyResVo());
        identifyVo = BeanUtil.copyProperties(identifyDto, new IdentifyVo());
        identifyVo.setRes(resVo);

        UserVo userVo = new UserVo();
        userVo.setUserId(auth.getUserId());
        identifyVo.setUser(userVo);

        IdentifyVo identify = identifyService.saveIdentifyWithRes(identifyVo);
        return R.ok(identify);
    }


}
