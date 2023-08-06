package edu.hfut.innovate.community.service;

import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.domain.vo.community.CollectionRecordVo;
import edu.hfut.innovate.community.entity.CollectionRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CollectionRecordService extends IService<CollectionRecord> {

    Set<Long> setOfCollectedTopics(Collection<Long> topicIds, Long userId);

    void saveCollectionRecord(CollectionRecord collectionRecord);

    void removeCollectionRecord(Long collectionId);

    List<CollectionRecordVo> listTopicCollectedByUserId(Long userId);

    PageUtils<CollectionRecordVo> queryPageByUserId(Map<String, Object> params, Long userId);

    Integer isCollectedTopic(Long topicId, Long userId);
}
