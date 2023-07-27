package edu.hfut.innovate.common.util;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * @author : Chowhound
 * @since : 2023/7/28 - 0:51
 */
@Data
public class BaseEntity {
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
    /**
     *
     */
    @TableLogic
    private Integer isDelete;
}
