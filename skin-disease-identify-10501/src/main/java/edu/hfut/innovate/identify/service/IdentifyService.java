package edu.hfut.innovate.identify.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.domain.dto.identify.IdentifyDto;
import edu.hfut.innovate.common.domain.vo.identify.IdentifyVo;
import edu.hfut.innovate.identify.entity.Identify;

import java.util.List;

/**
* @author hh825
* @description 针对表【identify】的数据库操作Service
* @createDate 2024-02-20 18:55:31
*/
public interface IdentifyService extends IService<Identify> {

    IdentifyVo getIdentifyById(Long identifyId);


    IdentifyVo saveIdentify(IdentifyDto identifyDto);


    IdentifyVo saveIdentifyWithRes(IdentifyVo identifyVo);

    IdentifyVo getByImg(String imgUrl, Long userId);

    List<IdentifyVo> getIdentifyByUserId(Long userId);
}
