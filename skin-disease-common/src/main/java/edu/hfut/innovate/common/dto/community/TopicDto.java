package edu.hfut.innovate.common.dto.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * @author Chowhound
 */
@ApiModel("话题(帖子)")
@Data
public class TopicDto implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@ApiModelProperty("标题")
	private String title;
	/**
	 * 
	 */
	@ApiModelProperty("用户")
	private Long userId;
	/**
	 * 
	 */
	@ApiModelProperty("内容")
	private String content;


	@ApiModelProperty("话题的标签")
	private List<String> tags;
}
