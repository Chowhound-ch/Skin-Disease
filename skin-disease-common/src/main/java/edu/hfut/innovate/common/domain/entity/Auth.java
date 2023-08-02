package edu.hfut.innovate.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import edu.hfut.innovate.common.domain.enums.RolesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@TableName(value ="auth")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Auth implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    private Long authId;

    private Long userId;

    private RolesEnum role;

    @Override
    public String toString() {
        return role.getRoleName();
    }
}