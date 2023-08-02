package edu.hfut.innovate.common.domain.dto.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@ApiModel("收藏记录")
@Data
public class CollectionRecordDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    @ApiModelProperty("收藏者")
    private Long userId;

    /**
     * 
     */
    @ApiModelProperty("收藏的帖子")
    private Long topicId;

}