package edu.hfut.innovate.community.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import edu.hfut.innovate.common.util.entity.LogicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@TableName(value ="forward_record")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ForwardRecord extends LogicEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    @TableId
    private Long forwardId;

    /**
     * 
     */
    private String forwardKey;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private Long topicId;

    public ForwardRecord(Long userId, Long topicId, String forwardKey) {
        this.userId = userId;
        this.topicId = topicId;
        this.forwardKey = forwardKey;
    }

}