package edu.hfut.innovate.common.domain.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Chowhound
 */
@Data
public class ElasticUserVo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Field(type = FieldType.Long, index = false)
	private Long userId;
	/**
	 * 
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String username;
	/**
	 *
	 */
	@Field(excludeFromSource = true)
	private String phone;
	/**
	 * 0：女，1：男，2：未知
	 */
	@Field(type = FieldType.Integer, index = false)
	private Integer sex;
	/**
	 * 
	 */
	@Field(type = FieldType.Integer, index = false)
	private Integer age;
	/**
	 * 
	 */
	@Field(type = FieldType.Keyword, index = false)
	private String avatar;
	/**
	 * 
	 */
	@Field(type = FieldType.Integer, index = false)
	private Integer likes;
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
