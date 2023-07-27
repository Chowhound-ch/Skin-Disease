package edu.hfut.innovate.community.service.impl;

import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import edu.hfut.innovate.community.dao.TopicDao;
import edu.hfut.innovate.community.entity.TopicEntity;
import edu.hfut.innovate.community.service.TopicService;


@Service("topicService")
public class TopicServiceImpl extends ServiceImpl<TopicDao, TopicEntity> implements TopicService {

    @Override
    public PageUtils<TopicEntity> queryPage(Map<String, Object> params) {
        IPage<TopicEntity> page = this.page(
                new Query<TopicEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils<>(page);
    }

}