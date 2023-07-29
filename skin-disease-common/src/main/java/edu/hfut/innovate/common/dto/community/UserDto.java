package edu.hfut.innovate.common.dto.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 
 * 
 * @author Chowhound
 */
@ApiModel("用户")
@Data
public class UserDto implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@ApiModelProperty("用户名")
	private String username;
	/**
	 *
	 */
	@ApiModelProperty("手机号")
	private String phone;
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
	@ApiModelProperty("总获赞数")
	private Integer likes;
}
