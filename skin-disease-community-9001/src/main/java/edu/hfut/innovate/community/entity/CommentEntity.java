package edu.hfut.innovate.community.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@ApiModel("评论")
@Data
@TableName("comment")
public class CommentEntity extends BaseEntity implements Serializable {
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
}
