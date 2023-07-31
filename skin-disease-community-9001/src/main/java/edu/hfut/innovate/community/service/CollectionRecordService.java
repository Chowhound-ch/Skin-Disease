package edu.hfut.innovate.community.service;

import edu.hfut.innovate.community.entity.CollectionRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.Set;

public interface CollectionRecordService extends IService<CollectionRecord> {

    Set<Long> setOfCollectedTopics(Collection<Long> topicIds, Long userId);
}
