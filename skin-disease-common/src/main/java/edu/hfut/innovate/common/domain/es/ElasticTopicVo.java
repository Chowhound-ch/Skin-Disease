package edu.hfut.innovate.common.domain.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author Chowhound
 */
@Document(indexName = "topic")
@Data
public class ElasticTopicVo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private String  esId;
	/**
	 * 
	 */
	@Field(type = FieldType.Long, index = false)
	private Long topicId;
	/**
	 * 
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String title;
	/**
	 * 
	 */
	@Field(type = FieldType.Object)
	private ElasticUserVo user;

	@Field(type = FieldType.Integer, index = false)
	private Integer isAnonymous;

	@Field(type = FieldType.Object)
	private ElasticTopicLocationVo location;
	/**
	 * 
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String content;
	/**
	 * 
	 */
	@Field(type = FieldType.Integer, index = false)
	private Integer likes;
	/**
	 * 
	 */
	@Field(type = FieldType.Integer, index = false)
	private Integer click;
	/**
	 * 
	 */
	@Field(type = FieldType.Integer, index = false)
	private Integer forward;
	/**
	 * 
	 */
	@Field(excludeFromSource = true)
	private Integer isLiked;

	@Field(excludeFromSource = true)
	private Integer isCollected;

	@Field(type = FieldType.Integer, index = false)
	private Integer collect;

	@Field(type = FieldType.Object)
	private List<ElasticTopicTagVo> tags;

	@Field(type = FieldType.Object)
	private List<ElasticCommentVo> comments;

	/**
	 * 
	 */
	@Field(type = FieldType.Date,index = false)
	private Date updateTime;
	/**
	 * 
	 */
	@Field(type = FieldType.Date,index = false)
	private Date createTime;

}
