package edu.hfut.innovate.common.vo.community;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Chowhound
 */
@ApiModel("用户")
@Data
@TableName("user")
public class UserVo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long userId;
	/**
	 * 
	 */
	@ApiModelProperty("用户名")
	private String username;
	/**
	 * 0：女，1：男，2：未知
	 */
	@ApiModelProperty("性别")
	private Integer sex;
	/**
	 * 
	 */
	@ApiModelProperty("年龄")
	private Integer age;
	/**
	 * 
	 */
	@ApiModelProperty("头像的url")
	private String avatar;
	/**
	 * 
	 */
	@ApiModelProperty("点赞数")
	private Integer likes;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 
	 */
	private Date createTime;
}
