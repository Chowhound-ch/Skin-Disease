package edu.hfut.innovate.common.domain.vo.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Chowhound
 */
@ApiModel("回复(评论的回复或者回复的回复)")
@Data
public class ReplyVo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Long replyId;
	/**
	 * 
	 */
	@ApiModelProperty("回复者")
	private UserVo user;

	@ApiModelProperty("被回复的帖子")
	private Long commentId;
	/**
	 * 
	 */
	@ApiModelProperty("被回复者")
	private Long replied;
	private UserVo repliedUser;
	/**
	 * 
	 */
	@ApiModelProperty("回复内容")
	private String content;
	/**
	 * 
	 */
	@ApiModelProperty("点赞数")
	private Integer likes;
	/**
	 * 1：回复层主（评论），0：回复其他人（其他人的回复）
	 *
	 * <p>回复层主（评论）的话显示的时候不需要加上@回复的人的名字</p>
	 * <p>回复其他人（其他人的回复）的话显示的时候需要加上@回复的人的名字</p>
	 */
	@ApiModelProperty("是否回复层主")
	private Integer isReplyTop;
	@ApiModelProperty("是否被当前用户点赞")
	private Integer isLiked;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 
	 */
	private Date createTime;

}
