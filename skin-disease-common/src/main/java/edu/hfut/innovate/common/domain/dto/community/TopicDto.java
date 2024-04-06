package edu.hfut.innovate.common.domain.dto.community;

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

	@ApiModelProperty("是否匿名")
	private Integer isAnonymous;

	@ApiModelProperty("位置的id")
	private Long locationId;


	@ApiModelProperty("话题的标签")
	private List<Long> tagIds;

	private List<String> imgs;

	private Integer sort;
}
