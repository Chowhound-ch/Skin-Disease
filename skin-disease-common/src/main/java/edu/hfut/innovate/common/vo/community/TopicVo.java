package edu.hfut.innovate.common.vo.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author Chowhound
 */
@ApiModel("话题(帖子)")
@Data
public class TopicVo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Long topicId;
	/**
	 * 
	 */
	@ApiModelProperty("标题")
	private String title;
	/**
	 * 
	 */
	@ApiModelProperty("用户")
	private UserVo user;
	/**
	 * 
	 */
	@ApiModelProperty("内容")
	private String content;
	/**
	 * 
	 */
	@ApiModelProperty("点赞数")
	private Integer likes;
	/**
	 * 
	 */
	@ApiModelProperty("点击数")
	private Integer click;
	/**
	 * 
	 */
	@ApiModelProperty("转发数")
	private Integer forward;
	/**
	 * 
	 */
	@ApiModelProperty("是否被当前用户点赞")
	private Integer isLike;
	@ApiModelProperty("是否被当前用户收藏")
	private Integer isCollect;

	@ApiModelProperty("收藏数")
	private Integer collect;

	@ApiModelProperty("话题的标签")
	private List<String> tags;
	@ApiModelProperty("话题的评论")
	private List<CommentVo> comments;

	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 
	 */
	private Date createTime;

}
