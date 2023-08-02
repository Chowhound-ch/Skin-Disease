package edu.hfut.innovate.common.domain.vo.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("点赞记录")
public class LikeRecordVo implements Serializable {
    /**
     * 
     */
    private Long likeId;

    /**
     * 
     */
    @ApiModelProperty("点赞者")
    private Long userId;

    /**
     * 
     */
    @ApiModelProperty("点赞的目标id")
    private Long desId;

    /**
     * 
     */
    @ApiModelProperty("点赞的目标类型, 1: 话题, 2: 评论, 3: 回复")
    private Integer desType;

    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private Date createTime;
}