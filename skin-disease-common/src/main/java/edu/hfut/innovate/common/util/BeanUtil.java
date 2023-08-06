package edu.hfut.innovate.common.util;

import org.springframework.beans.BeanUtils;

/**
 * @author : Chowhound
 * @since : 2023/7/23 - 16:31
 */
public class BeanUtil {

    public static <T> T copyProperties(Object source, T targetClass) {
        if (source == null) {
            return null;
        }
        BeanUtils.copyProperties(source, targetClass);
        return targetClass;
    }
}
