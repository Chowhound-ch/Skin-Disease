package edu.hfut.innovate.community.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.domain.vo.community.CollectionRecordVo;
import edu.hfut.innovate.common.domain.vo.community.TopicVo;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.Query;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.community.dao.CollectionRecordMapper;
import edu.hfut.innovate.community.entity.CollectionRecord;
import edu.hfut.innovate.community.service.CollectionRecordService;
import edu.hfut.innovate.community.service.TopicService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CollectionRecordServiceImpl extends ServiceImpl<CollectionRecordMapper, CollectionRecord>
    implements CollectionRecordService, ApplicationRunner {
    private TopicService topicService;


    @Override
    public Set<Long> setOfCollectedTopics(Collection<Long> topicIds, Long userId) {
        List<CollectionRecord> collectionRecordList = list(new LambdaQueryWrapper<CollectionRecord>()
                .in(CollectionRecord::getTopicId, topicIds)
                .eq(CollectionRecord::getUserId, userId));

        return collectionRecordList.stream()
                .map(CollectionRecord::getTopicId)
                .collect(Collectors.toSet());
    }

    @Override
    public void saveCollectionRecord(CollectionRecord collectionRecord) {
        saveOrUpdate(collectionRecord, getEQWrapper(
                collectionRecord.getUserId(), collectionRecord.getTopicId()));

        topicService.offsetTopicCollectionCount(collectionRecord.getTopicId(), 1);

    }

    @Override
    public void removeCollectionRecord(Long collectionId) {

        CollectionRecord collectionRecord = getById(collectionId);
        if (collectionRecord == null) { // 没有收藏记录
            return;
        }

        topicService.offsetTopicCollectionCount(collectionRecord.getTopicId(), -1);
        removeById(collectionId);

    }

    @Override
    public List<CollectionRecordVo> listTopicCollectedByUserId(Long userId) {
        List<CollectionRecord> collectionRecordList = list(new LambdaQueryWrapper<CollectionRecord>().eq(CollectionRecord::getUserId, userId));

        Collection<Long> ids = CollectionUtil.getCollection(collectionRecordList, CollectionRecord::getTopicId);

        // 收藏列表里查看的话，不需要附带comment
        Map<Long, TopicVo> topicVoMap = topicService.mapByTopicIds(ids);

        return collectionRecordList.stream().map( collectionRecord -> {
            CollectionRecordVo collectionRecordVo = BeanUtil.copyProperties(collectionRecord, new CollectionRecordVo());
            collectionRecordVo.setTopic(topicVoMap.get(collectionRecord.getTopicId()));
            return collectionRecordVo;
        }).toList();
    }

    @Override
    public PageUtils<CollectionRecordVo> queryPageByUserId(Map<String, Object> params, Long userId) {
        IPage<CollectionRecord> page = this.page(
                new Query<CollectionRecord>().getPage(params),
                new LambdaQueryWrapper<CollectionRecord>().eq(CollectionRecord::getUserId, userId));
        Collection<Long> topicIds = CollectionUtil.getCollection(page.getRecords(), CollectionRecord::getTopicId);

        Map<Long, TopicVo> topicVoMap = topicService.mapByTopicIds(topicIds);

        List<CollectionRecordVo> collectionRecordVos = page.getRecords().stream().map(collectionRecord -> {
            CollectionRecordVo collectionRecordVo = BeanUtil.copyProperties(collectionRecord, new CollectionRecordVo());
            collectionRecordVo.setTopic(topicVoMap.get(collectionRecord.getTopicId()));
            return collectionRecordVo;
        }).toList();

        return new PageUtils<>(collectionRecordVos, page.getTotal(), page.getSize(), page.getCurrent());
    }

    @Override
    public Integer isCollectedTopic(Long topicId, Long userId) {

        CollectionRecord collectionRecord = getOne(getEQWrapper(userId, topicId));
        return collectionRecord == null ? 0 : 1;
    }


    private LambdaQueryWrapper<CollectionRecord> getEQWrapper(Long userId, Long topicId) {
        return new LambdaQueryWrapper<CollectionRecord>()
                .eq(CollectionRecord::getUserId, userId)
                .eq(CollectionRecord::getTopicId, topicId);
    }

    @Override
    public void run(ApplicationArguments args) {
        topicService = SpringUtil.getBean(TopicService.class);
    }
}




