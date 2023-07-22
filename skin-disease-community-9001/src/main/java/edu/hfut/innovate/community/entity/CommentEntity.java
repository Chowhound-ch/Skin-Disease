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
@ApiModel("评论")
@Data
@TableName("comment")
public class CommentEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long commentId;
	/**
	 * 
	 */
	private Long userId;
	/**
	 * 
	 */
	private Long topicId;
	/**
	 * 
	 */
	private Integer likes;
	/**
	 * 
	 */
	private String content;
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
