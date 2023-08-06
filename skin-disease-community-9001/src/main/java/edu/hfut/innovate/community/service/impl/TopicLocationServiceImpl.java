package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.domain.vo.community.TopicLocationVo;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.community.dao.TopicLocationMapper;
import edu.hfut.innovate.community.entity.TopicLocationEntity;
import edu.hfut.innovate.community.service.TopicLocationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TopicLocationServiceImpl extends ServiceImpl<TopicLocationMapper, TopicLocationEntity>
    implements TopicLocationService{

    @Override
    public Map<Long, TopicLocationVo> mapTopicLocationById(Collection<Long> topicIds) {
        List<TopicLocationEntity> entities = this.listByIds(topicIds);

        return entities.stream()
                .map(entity -> BeanUtil.copyProperties(entity, new TopicLocationVo()))
                .collect(Collectors.toMap(TopicLocationVo::getLocationId, Function.identity()));
    }


}




