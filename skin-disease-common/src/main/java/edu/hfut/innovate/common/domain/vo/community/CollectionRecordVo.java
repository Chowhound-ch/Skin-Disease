package edu.hfut.innovate.common.domain.vo.community;

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
    private TopicVo topic;

    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private Date createTime;
}