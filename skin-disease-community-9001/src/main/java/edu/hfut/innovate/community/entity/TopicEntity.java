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
import java.util.List;


/**
 * 
 * 
 * @author Chowhound
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("话题(帖子)")
@Data
@TableName("topic")
public class TopicEntity extends BaseEntity implements Serializable {
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
	@TableField(exist = false)
	private UserEntity user;
	/**
	 * 
	 */
	private String content;

	private String imgs;

	private Integer isAnonymous;

	private String anonymousName;

	private Long locationId;
	@TableField(exist = false)
	private TopicLocationEntity location;
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

	private Integer sort;

	@TableField(exist = false)
	private List<TopicTagEntity> tags;

}
