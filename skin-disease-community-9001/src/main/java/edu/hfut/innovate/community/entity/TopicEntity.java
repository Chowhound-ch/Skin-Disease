package edu.hfut.innovate.community.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Chowhound
 */
@Data
@TableName("topic")
public class TopicEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long topicId;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private Long userId;
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
	private Integer click;
	/**
	 * 
	 */
	private Integer forward;
	/**
	 * 
	 */
	private Integer collect;
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
