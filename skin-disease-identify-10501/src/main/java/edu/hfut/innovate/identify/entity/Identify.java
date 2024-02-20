package edu.hfut.innovate.identify.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import edu.hfut.innovate.common.util.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @TableName identify
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="identify")
@Data
public class Identify extends BaseEntity implements Serializable {
    private Long identifyId;

    private Long userId;

    private String imgUrl;

    private Long resId;


    @Serial
    private static final long serialVersionUID = 1L;
}