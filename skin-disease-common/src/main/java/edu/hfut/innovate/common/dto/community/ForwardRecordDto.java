package edu.hfut.innovate.common.dto.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@ApiModel("转发记录")
@Data
public class ForwardRecordDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    @ApiModelProperty("转发记录的key")
    private String forwardKey;

    /**
     * 
     */
    @ApiModelProperty("转发者")
    private Long userId;

    @ApiModelProperty("转发的目标id")
    private Long topicId;
}