package edu.hfut.innovate.community.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 
 * 
 * @author Chowhound
 */
@Data
@TableName("topic_tag_relation")
public class TopicTagRelationEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long tagRelationId;
	/**
	 * 
	 */
	private Long topicId;
	/**
	 * 
	 */
	private Long tagId;

}