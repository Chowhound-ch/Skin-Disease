package edu.hfut.innovate.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.hfut.innovate.common.domain.vo.community.ForwardRecordVo;
import edu.hfut.innovate.community.entity.ForwardRecord;

public interface ForwardRecordService extends IService<ForwardRecord> {

    ForwardRecordVo getForwardRecordByUserId(Long topicId, Long userId);

    ForwardRecordVo getForwardRecordByKey(String forwardKey);
}
