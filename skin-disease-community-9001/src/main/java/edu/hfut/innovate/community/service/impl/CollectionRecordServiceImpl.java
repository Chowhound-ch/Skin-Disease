package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.community.entity.CollectionRecord;
import edu.hfut.innovate.community.service.CollectionRecordService;
import edu.hfut.innovate.community.dao.CollectionRecordMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CollectionRecordServiceImpl extends ServiceImpl<CollectionRecordMapper, CollectionRecord>
    implements CollectionRecordService{

    @Override
    public Set<Long> setOfCollectedTopics(Collection<Long> topicIds, Long userId) {
        List<CollectionRecord> collectionRecordList = list(new LambdaQueryWrapper<CollectionRecord>()
                .in(CollectionRecord::getTopicId, topicIds)
                .eq(CollectionRecord::getUserId, userId));

        return collectionRecordList.stream()
                .map(CollectionRecord::getTopicId)
                .collect(Collectors.toSet());
    }
}




