package edu.hfut.innovate.common.domain.vo.community;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 
 * 
 * @author Chowhound
 */
@ApiModel("话题的标签")
@Data
public class TopicTagVo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Long tagId;
	/**
	 * 
	 */
	private String name;
}
