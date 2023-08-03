package edu.hfut.innovate.common.domain.dto.community;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author : Chowhound
 * @since : 2023/8/3 - 18:47
 */
@ApiModel("wechat用户信息")
@Data
public class WeChatUserInfoDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名")
    private String nickName;

    @ApiModelProperty("微信code")
    private String jsCode;

    @ApiModelProperty("头像")
    private String avatarUrl;
    /**
     * 2：女，1：男，0：未知
     */
    @ApiModelProperty("性别")
    private Integer gender;


    @ApiModelProperty("语言")
    private String language;

    @ApiModelProperty("省份")
    private String province;
}
