package edu.hfut.innovate.common.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author : Chowhound
 * @since : 2023/8/2 - 22:26
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAuth {

    /**
     *
     */
    private Long userId;
    /**
     *
     */
    private String username;
    /**
     *
     */
    private String phone;



    private List<String> roles;
    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private Date createTime;
}
