package edu.hfut.innovate.community.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
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
@TableName("reply")
public class ReplyEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long replyId;
	/**
	 * 
	 */
	private Long userId;
	/**
	 * 
	 */
	private Long replied;


	private Long commentId;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private Integer likes;
	/**
	 * 
	 */
	private Integer isReplyTop;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Integer isDelete;

}
