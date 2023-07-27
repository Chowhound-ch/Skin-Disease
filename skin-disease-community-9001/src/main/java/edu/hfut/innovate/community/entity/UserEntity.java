package edu.hfut.innovate.community.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 
 * 
 * @author Chowhound
 */
@ApiModel("用户")
@Data
@TableName("user")
public class UserEntity implements Serializable {
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
	private String username;
	/**
	 * 
	 */
	private String password;
	/**
	 *
	 */
	private String phone;
	/**
	 * 0：女，1：男，2：未知
	 */
	private Integer sex;
	/**
	 * 
	 */
	private Integer age;
	/**
	 * 
	 */
	private String avatar;
	/**
	 * 
	 */
	private Integer likes;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	@TableLogic
	private Integer isDelete;

}
