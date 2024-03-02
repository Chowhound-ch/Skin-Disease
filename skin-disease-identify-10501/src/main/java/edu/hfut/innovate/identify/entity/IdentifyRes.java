package edu.hfut.innovate.identify.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import edu.hfut.innovate.common.util.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @TableName identify_res
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="identify_res")
@Data
public class IdentifyRes extends BaseEntity implements Serializable {
    @TableId
    private Long resId;

    private Integer normal;

    private String msg;

    private String most;


    @Serial
    private static final long serialVersionUID = 1L;
}