package edu.hfut.innovate.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import edu.hfut.innovate.common.util.entity.LogicEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName(value ="forward_record")
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
    private Long forwardKey;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private Long topicId;

    /**
     * 
     */
    private Long isDelete;

}