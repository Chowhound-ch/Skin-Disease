package edu.hfut.innovate.community.service;

import edu.hfut.innovate.common.domain.vo.community.RotationVo;
import edu.hfut.innovate.community.entity.RotationEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface RotationService extends IService<RotationEntity> {

    List<RotationVo> getEnabledRotationList();

    Boolean saveRotation(RotationEntity rotationEntity);

    List<RotationVo> getRotationList();
}
