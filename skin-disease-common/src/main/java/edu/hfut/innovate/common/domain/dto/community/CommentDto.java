package edu.hfut.innovate.common.domain.dto.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author : Chowhound
 * @since : 2023/7/23 - 13:26
 */
@ApiModel("评论")
@Data
public class CommentDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ApiModelProperty("评论者")
    private Long userId;
    /**
     *
     */
    @ApiModelProperty("评论的话题")
    private Long topicId;
    /**
     *
     */
    @ApiModelProperty("评论内容")
    private String content;
}
