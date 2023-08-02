package edu.hfut.innovate.common.domain.dto.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author : Chowhound
 * @since : 2023/7/29 - 22:52
 */
@Data
@ApiModel("点赞记录")
public class LikeRecordDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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
}
