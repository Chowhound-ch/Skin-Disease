package edu.hfut.innovate.common.util.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * @author : Chowhound
 * @since : 2023/7/28 - 1:27
 */
@Data
public class LogicEntity {
    /**
     *
     */
    @TableLogic
    private Integer isDelete;
}
