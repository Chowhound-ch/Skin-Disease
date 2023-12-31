package edu.hfut.innovate.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import edu.hfut.innovate.common.util.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@TableName(value ="like_record")
@Data
public class LikeRecord extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long likeId;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private Long desId;

    /**
     * 
     */
    private Integer desType;

}