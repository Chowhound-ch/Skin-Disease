package edu.hfut.innovate.identify.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.domain.dto.identify.IdentifyDto;
import edu.hfut.innovate.common.domain.vo.community.UserVo;
import edu.hfut.innovate.common.domain.vo.identify.IdentifyResVo;
import edu.hfut.innovate.common.domain.vo.identify.IdentifyVo;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.identify.entity.Identify;
import edu.hfut.innovate.identify.entity.IdentifyRes;
import edu.hfut.innovate.identify.mapper.IdentifyMapper;
import edu.hfut.innovate.identify.service.IdentifyResService;
import edu.hfut.innovate.identify.service.IdentifyService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

            return identifyVo;
        }

        return null;
    }

    @Override
    public IdentifyVo saveIdentify(IdentifyDto identifyDto) {
        Identify identify = new Identify();
        identify.setUserId(identifyDto.getUserId());
        identify.setImgUrl(identifyDto.getImgUrl());
        if (this.save(identify)) {
            IdentifyVo identifyVo = BeanUtil.copyProperties(identify, new IdentifyVo());
            UserVo userVo = new UserVo();
            userVo.setUserId(identify.getUserId());
            identifyVo.setUser(userVo);
            return identifyVo;
        }

        return null;
    }

    @Override
    public IdentifyVo saveIdentifyWithRes(IdentifyVo identifyVo) {
        Identify identify = BeanUtil.copyProperties(identifyVo, new Identify());
        IdentifyRes res = BeanUtil.copyProperties(identifyVo.getRes(), new IdentifyRes());
        identify.setUserId(identifyVo.getUser().getUserId());
        resService.save(res);
        identify.setResId(res.getResId());
        this.save(identify);


        IdentifyVo vo = BeanUtil.copyProperties(identify, new IdentifyVo());
        UserVo userVo = new UserVo();
        userVo.setUserId(identify.getUserId());
        vo.setUser(userVo);
        vo.setRes(BeanUtil.copyProperties(res, new IdentifyResVo()));

        return vo;
    }

    @Override
    public IdentifyVo getByImg(String imgUrl, Long userId) {
        LambdaQueryWrapper<Identify> queryWrapper = new LambdaQueryWrapper<Identify>().eq(Identify::getImgUrl, imgUrl).eq(Identify::getUserId, userId);
        Identify one = this.getOne(queryWrapper);
        if (one == null) return null;
        IdentifyVo identifyVo = BeanUtil.copyProperties(one, new IdentifyVo());
        UserVo userVo = new UserVo();
        userVo.setUserId(userId);
        identifyVo.setUser(userVo);
        IdentifyRes res = resService.getById(one.getResId());
        IdentifyResVo resVo = BeanUtil.copyProperties(res, new IdentifyResVo());
        identifyVo.setRes(resVo);
        return identifyVo;
    }

    @Override
    public List<IdentifyVo> getIdentifyByUserId(Long userId) {
        LambdaQueryWrapper<Identify> queryWrapper = new LambdaQueryWrapper<Identify>().eq(Identify::getUserId, userId);
        List<Identify> identifyList = this.list(queryWrapper);
        if (identifyList.isEmpty()) return Collections.emptyList();

        Collection<Long> resIds = CollectionUtil.getCollection(identifyList, Identify::getResId);
        List<IdentifyRes> identifyRes = resService.listByIds(resIds);
        Map<Long, IdentifyRes> resMap = CollectionUtil.getMap(identifyRes, IdentifyRes::getResId);

        return identifyList.stream().map(identify -> {
            IdentifyVo identifyVo = BeanUtil.copyProperties(identify, new IdentifyVo());
            IdentifyRes res = resMap.get(identify.getResId());
            identifyVo.setRes(BeanUtil.copyProperties(res, new IdentifyResVo()));
            UserVo userVo = new UserVo();
            userVo.setUserId(identify.getUserId());
            identifyVo.setUser(userVo);
            return identifyVo;
        }).toList();
    }

}




