package edu.hfut.innovate.common.domain.es;

import edu.hfut.innovate.common.domain.vo.community.*;
import edu.hfut.innovate.common.util.BeanUtil;
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
	@Field(type = FieldType.Text, index = false)
	private List<String> imgs;

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

	@Field(type = FieldType.Integer)
	private Integer sort;

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

	public static ElasticTopicVo get(TopicVo topicVo) {
		ElasticTopicVo elasticTopicVo = BeanUtil.copyProperties(topicVo, new ElasticTopicVo());
		elasticTopicVo.setUser(BeanUtil.copyProperties(topicVo.getUser(), new ElasticUserVo()));
		elasticTopicVo.setTags(BeanUtil.copyPropertiesList(topicVo.getTags(), ElasticTopicTagVo.class));
		elasticTopicVo.setLocation(BeanUtil.copyProperties(topicVo.getLocation(), new ElasticTopicLocationVo()));
		if (topicVo.getComments() != null) {
			List<ElasticCommentVo> commentVos = topicVo.getComments().stream().map(commentVo -> {
				ElasticCommentVo elasticCommentVo = BeanUtil.copyProperties(commentVo, new ElasticCommentVo());
				elasticCommentVo.setUser(BeanUtil.copyProperties(commentVo.getUser(), new ElasticUserVo()));
				return elasticCommentVo;
			}).toList();
			elasticTopicVo.setComments(commentVos);

		}


		return elasticTopicVo;
	}

	public TopicVo toTopicVo(){
		TopicVo topicVo = BeanUtil.copyProperties(this, new TopicVo());

		topicVo.setUser(BeanUtil.copyProperties(this.getUser(), new UserVo()));
		topicVo.setTags(BeanUtil.copyPropertiesList(this.getTags(), TopicTagVo.class));
		topicVo.setLocation(BeanUtil.copyProperties(this.getLocation(), new TopicLocationVo()));
		if (this.getComments() != null) {
			List<CommentVo> commentVos = this.getComments().stream().map(elasticCommentVo -> {
				CommentVo commentVo = BeanUtil.copyProperties(elasticCommentVo, new CommentVo());
				commentVo.setUser(BeanUtil.copyProperties(elasticCommentVo.getUser(), new UserVo()));
				return commentVo;
			}).toList();

			topicVo.setComments(commentVos);
		}
		return topicVo;
	}

}
