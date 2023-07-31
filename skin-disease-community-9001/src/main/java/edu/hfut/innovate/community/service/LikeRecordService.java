package edu.hfut.innovate.community.service;

import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.community.entity.LikeRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface LikeRecordService extends IService<LikeRecord> {

    void saveLikeRecord(LikeRecord likeRecord);

    LikeRecord getLikeRecord(Long userId, Long desId, Integer desType);

    List<LikeRecord> listTopicLikedByUserId(Long userId);

    void removeLikeRecord(Long likeId);

    PageUtils<LikeRecord> queryPageByUserId(Map<String, Object> params, Long userId);
}
