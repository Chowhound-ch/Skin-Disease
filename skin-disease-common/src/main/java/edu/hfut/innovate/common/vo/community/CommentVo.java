package edu.hfut.innovate.common.vo.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : Chowhound
 * @since : 2023/7/23 - 13:26
 */
@ApiModel("评论")
@Data
public class CommentVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long commentId;
    /**
     *
     */
    @ApiModelProperty("评论者")
    private UserVo user;
    /**
     *
     */
    @ApiModelProperty("评论的话题")
    private TopicVo topic;
    /**
     *
     */
    @ApiModelProperty("点赞")
    private Integer likes;
    /**
     *
     */
    @ApiModelProperty("评论内容")
    private String content;
    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private Date createTime;
}
