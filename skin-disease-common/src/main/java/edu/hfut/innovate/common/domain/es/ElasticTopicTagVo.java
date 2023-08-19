package edu.hfut.innovate.common.domain.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;

/**
 * 
 * 
 * @author Chowhound
 */
@Data
public class ElasticTopicTagVo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Field(type = FieldType.Long, index = false)
	private Long tagId;
	/**
	 * 
	 */
	@Field(type = FieldType.Keyword)
	private String name;
}
