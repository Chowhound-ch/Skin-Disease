package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.domain.dto.community.RotationDto;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.community.entity.RotationEntity;
import edu.hfut.innovate.community.service.RotationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Chowhound
 * @since : 2023/9/9 - 10:58
 */

@RequestMapping("/rotation")
@RestController
public class RotationController {
    @Autowired
    private RotationService rotationService;


    @GetMapping("/list/enabled")
    @ApiOperation("获得轮播图")
    public R getRotations(@RequestHeader("Authorization") String token){
//        UserAuth userAuth = tokenManager.getUserFromTokenWithBearer(token);

        return R.ok(rotationService.getEnabledRotationList());
    }

    @GetMapping("/list/all")
    @ApiOperation("获得轮播图")
    public R getRotationsAll(@RequestHeader("Authorization") String token){
//        UserAuth userAuth = tokenManager.getUserFromTokenWithBearer(token);

        return R.ok(rotationService.getRotationList());
    }


    @PostMapping("/save")
    @ApiOperation("获得轮播图")
    public R saveRotation(@RequestBody RotationDto rotationDto){
        RotationEntity rotation = BeanUtil.copyProperties(rotationDto, new RotationEntity());

        rotationService.saveRotation(rotation);

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("更新轮播图")
    public R updateRotation(@RequestBody List<RotationDto> rotationDtoList){

        List<RotationEntity> rotationEntities = rotationDtoList.stream()
                .map(rotationDto -> BeanUtil.copyProperties(rotationDto, new RotationEntity()))
                .toList();

        rotationService.updateBatchById(rotationEntities);
        return R.ok();
    }
}
