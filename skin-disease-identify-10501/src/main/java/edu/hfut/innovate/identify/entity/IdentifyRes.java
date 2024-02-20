package edu.hfut.innovate.identify.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName identify_res
 */
@TableName(value ="identify_res")
@Data
public class IdentifyRes implements Serializable {
    private Long resId;

    private Integer normal;

    private String msg;

    private String most;

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}