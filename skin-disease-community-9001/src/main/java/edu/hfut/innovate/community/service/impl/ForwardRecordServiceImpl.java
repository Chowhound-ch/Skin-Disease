package edu.hfut.innovate.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.vo.community.ForwardRecordVo;
import edu.hfut.innovate.community.dao.ForwardRecordMapper;
import edu.hfut.innovate.community.entity.ForwardRecord;
import edu.hfut.innovate.community.service.ForwardRecordService;
import edu.hfut.innovate.community.util.ForwardUtil;
import org.springframework.stereotype.Service;

@Service
public class ForwardRecordServiceImpl extends ServiceImpl<ForwardRecordMapper, ForwardRecord>
    implements ForwardRecordService{


    @Override
    public ForwardRecordVo getForwardRecordByUserId(Long topicId, Long userId) {
        String forwardKey = ForwardUtil.generateForwardKey(userId, topicId);

        ForwardRecord forwardRecord = this.getOne(new LambdaQueryWrapper<ForwardRecord>()
                .eq(ForwardRecord::getForwardKey, forwardKey));

        if (forwardRecord == null) {
            forwardRecord = new ForwardRecord(userId, topicId, forwardKey);
            this.save(forwardRecord);
        }

        return BeanUtil.copyProperties(forwardRecord, new ForwardRecordVo());
    }

    @Override
    public ForwardRecordVo getForwardRecordByKey(String forwardKey) {
        ForwardRecord record = getOne(new LambdaQueryWrapper<ForwardRecord>()
                .eq(ForwardRecord::getForwardKey, forwardKey));

        return BeanUtil.copyProperties(record, new ForwardRecordVo());
    }
}




