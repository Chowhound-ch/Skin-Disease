package edu.hfut.innovate.common.vo.community;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class CollectionRecordVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    private Long collectionId;

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

    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private Date createTime;
}