package edu.hfut.innovate.common.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author : Chowhound
 * @since : 2023/8/2 - 22:30
 */
@Getter
public enum RolesEnum {
    USER("ROLE_USER", 0),

    ADMIN("ROLE_ADMIN", 1);

    private final String roleName;
    @EnumValue
    private final Integer value;

    RolesEnum(String roleName, Integer value) {
        this.roleName = roleName;
        this.value = value;
    }
}
