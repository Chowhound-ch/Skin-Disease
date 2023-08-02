package edu.hfut.innovate.community.service;

import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.domain.vo.community.LikeRecordVo;
import edu.hfut.innovate.community.entity.LikeRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface LikeRecordService extends IService<LikeRecord> {

    void saveLikeRecord(LikeRecord likeRecord);

    LikeRecord getLikeRecord(Long userId, Long desId, Integer desType);

    List<LikeRecordVo> listTopicLikedByUserId(Long userId);

    void removeLikeRecord(Long likeId);

    PageUtils<LikeRecordVo> queryPageByUserId(Map<String, Object> params, Long userId);

    Set<Long> setOfLikedDesIds(Collection<Long> desIds, Long userId, Integer desType);

    Integer isLikedDesId(Long desId, Long userId, Integer desType);
}
