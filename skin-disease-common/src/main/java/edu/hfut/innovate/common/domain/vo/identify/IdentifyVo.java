package edu.hfut.innovate.common.domain.vo.identify;

import edu.hfut.innovate.common.domain.vo.community.UserVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : Chowhound
 * @since : 2024/2/21 - 13:47
 */
@Data
public class IdentifyVo implements Serializable {
    private Long identifyId;

    private UserVo user;

    private String imgUrl;

    private IdentifyResVo res;

    private Date updateTime;

    private Date createTime;
}
