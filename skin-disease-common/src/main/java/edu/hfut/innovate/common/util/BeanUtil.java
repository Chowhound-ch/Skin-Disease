package edu.hfut.innovate.common.util;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.List;

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

    public static <T> List<T> copyPropertiesList(List<?> source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            Constructor<T> constructor = targetClass.getDeclaredConstructor();
            return source.stream().map(o -> {
                try {
                    return copyProperties(o, constructor.newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).toList();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
