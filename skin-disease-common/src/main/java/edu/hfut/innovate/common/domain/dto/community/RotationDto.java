package edu.hfut.innovate.common.domain.dto.community;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@ApiModel("轮播图")
@Data
public class RotationDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("轮播图id")
    @TableId
    private Long rotationId;


    @ApiModelProperty("轮播图标题")
    private String title;


    @ApiModelProperty("轮播图url")
    private String url;


    @ApiModelProperty("轮播图是否启用")
    private Integer enable;


    @ApiModelProperty("轮播图顺序")
    private Integer order;
}