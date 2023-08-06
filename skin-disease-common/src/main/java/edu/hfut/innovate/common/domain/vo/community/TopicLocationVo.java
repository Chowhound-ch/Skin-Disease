package edu.hfut.innovate.common.domain.vo.community;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class TopicLocationVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long locationId;

    private String locationName;

}