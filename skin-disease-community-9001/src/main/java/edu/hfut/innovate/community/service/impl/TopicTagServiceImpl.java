package edu.hfut.innovate.community.service.impl;

import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import edu.hfut.innovate.community.dao.TopicTagDao;
import edu.hfut.innovate.community.entity.TopicTagEntity;
import edu.hfut.innovate.community.service.TopicTagService;


@Service("topicTagService")
public class TopicTagServiceImpl extends ServiceImpl<TopicTagDao, TopicTagEntity> implements TopicTagService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TopicTagEntity> page = this.page(
                new Query<TopicTagEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

}