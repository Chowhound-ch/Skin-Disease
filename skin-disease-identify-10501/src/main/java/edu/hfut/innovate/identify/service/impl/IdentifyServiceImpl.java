package edu.hfut.innovate.identify.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.domain.vo.identify.IdentifyResVo;
import edu.hfut.innovate.common.domain.vo.identify.IdentifyVo;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.identify.entity.Identify;
import edu.hfut.innovate.identify.entity.IdentifyRes;
import edu.hfut.innovate.identify.mapper.IdentifyMapper;
import edu.hfut.innovate.identify.service.IdentifyResService;
import edu.hfut.innovate.identify.service.IdentifyService;
import org.springframework.stereotype.Service;

/**
* @author hh825
* @description 针对表【identify】的数据库操作Service实现
* @createDate 2024-02-20 18:55:31
*/
@Service
public class IdentifyServiceImpl extends ServiceImpl<IdentifyMapper, Identify>
    implements IdentifyService{
    private final IdentifyResService resService;

    public IdentifyServiceImpl(IdentifyResService resService) {
        this.resService = resService;
    }

    @Override
    public IdentifyVo getIdentifyById(Long identifyId) {
        Identify identify = this.getById(identifyId);
        IdentifyVo identifyVo = BeanUtil.copyProperties(identify, new IdentifyVo());

        if (identifyVo != null && identify.getResId() != null) {
            IdentifyRes res = resService.getById(identify.getResId());
            IdentifyResVo resVo = BeanUtil.copyProperties(res, new IdentifyResVo());
            identifyVo.setRes(resVo);



        }

        return null;
    }
}




