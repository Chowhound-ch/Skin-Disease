package edu.hfut.innovate.community.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import edu.hfut.innovate.common.util.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 
 * 
 * @author Chowhound
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("回复(评论的回复或者回复的回复)")
@Data
@TableName("reply")
public class ReplyEntity extends BaseEntity implements Serializable {
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

	@TableField(exist = false)
	private UserEntity user;
	@TableField(exist = false)
	private UserEntity repliedUser;
}
