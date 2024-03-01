package edu.hfut.innovate.common.domain.vo.identify;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * @author : Chowhound
 * @since : 2024/2/21 - 13:48
 */
@Data
public class IdentifyResVo implements Serializable {
    private Long resId;

    private Integer normal;

    private String msg;

    private String most;

    private Date updateTime;

    private Date createTime;

}
