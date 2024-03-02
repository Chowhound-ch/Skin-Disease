package edu.hfut.innovate.common.domain.dto.identify;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Chowhound
 * @since : 2024/2/21 - 13:47
 */
@Data
public class IdentifyDto implements Serializable {
    private Long userId;

    private String imgUrl;
}
