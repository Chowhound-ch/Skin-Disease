package edu.hfut.innovate.common.domain.dto.identify;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Chowhound
 * @since : 2024/2/21 - 13:48
 */
@Data
public class IdentifyResDto implements Serializable {
    private Long resId;

    private Integer normal;

    private String msg;

    private String most;
}
