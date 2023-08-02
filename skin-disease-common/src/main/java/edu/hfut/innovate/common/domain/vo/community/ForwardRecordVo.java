package edu.hfut.innovate.common.domain.vo.community;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ForwardRecordVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    private Long forwardId;

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

    /**
     * 
     */
    @ApiModelProperty("转发的目标id")
    private Long topicId;

}