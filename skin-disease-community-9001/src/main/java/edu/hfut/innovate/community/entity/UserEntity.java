package edu.hfut.innovate.community.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import edu.hfut.innovate.common.util.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 
 * @author Chowhound
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户")
@Data
@TableName("user")
public class UserEntity extends BaseEntity implements Serializable {
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
}
