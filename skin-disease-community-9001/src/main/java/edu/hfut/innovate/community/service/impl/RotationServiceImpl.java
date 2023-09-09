package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.domain.vo.community.RotationVo;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.community.entity.RotationEntity;
import edu.hfut.innovate.community.service.RotationService;
import edu.hfut.innovate.community.dao.RotationMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RotationServiceImpl extends ServiceImpl<RotationMapper, RotationEntity>
    implements RotationService{

    @Override
    public List<RotationVo> getEnabledRotationList() {
        List<RotationEntity> rotationList = this.list(getBaseWrapper().orderByAsc(RotationEntity::getOrder));

        return rotationList.stream().map(rotation -> BeanUtil.copyProperties(rotation, new RotationVo())).toList();
    }

    @Override
    public List<RotationVo> getRotationList() {
        List<RotationEntity> rotationList = this.list(new LambdaQueryWrapper<RotationEntity>().orderByAsc(RotationEntity::getOrder));

        return rotationList.stream().map(rotation -> BeanUtil.copyProperties(rotation, new RotationVo())).toList();
    }

    public Boolean saveRotation(RotationEntity rotationEntity){
        Integer maximumOrder = getMaximumOrder();
        rotationEntity.setRotationId(null);
        rotationEntity.setOrder(maximumOrder + 1);

        return save(rotationEntity);
    }

    public Integer getMaximumOrder(){
        LambdaQueryWrapper<RotationEntity> lambdaQueryWrapper = getBaseWrapper().orderByDesc(RotationEntity::getOrder).last("limit 1");

        RotationEntity rotationEntity = this.getOne(lambdaQueryWrapper);

        return rotationEntity == null? 0 : rotationEntity.getOrder();
    }
    private LambdaQueryWrapper<RotationEntity> getBaseWrapper(){
        return new LambdaQueryWrapper<RotationEntity>().eq(RotationEntity::getEnable, 1);
    }
}




