package edu.hfut.innovate.common.util.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author : Chowhound
 * @since : 2023/7/28 - 0:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseEntity extends LogicEntity {
    /**
     *
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public Date updateTime;
    /**
     *
     */
    // 插入时自动填充
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
