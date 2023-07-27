package edu.hfut.innovate.common.config.mybatis;

import cn.hutool.core.util.ClassUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import edu.hfut.innovate.common.util.entity.BaseEntity;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 自动填充添加、修改时间
 *
 * @author : Chowhound
 * @since : 2023/07/28 - 01:12
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static String CREATE_TIME_FIELD_NAME;
    private static String UPDATE_TIME_FIELD_NAME;

    static {
        for (Field field : ClassUtil.getDeclaredFields(BaseEntity.class)) {
            TableField annotation = field.getAnnotation(TableField.class);
            if (annotation != null) {
                if (annotation.fill().equals(FieldFill.INSERT)) {
                    CREATE_TIME_FIELD_NAME = field.getName();
                } else if (annotation.fill().equals(FieldFill.INSERT_UPDATE)) {
                    UPDATE_TIME_FIELD_NAME = field.getName();
                }
            }

        }
    }

    /**
     * 插入时的填充策略
     *
     * @param metaObject Clinton Begin
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName(CREATE_TIME_FIELD_NAME, new Date(), metaObject);
        this.setFieldValByName(UPDATE_TIME_FIELD_NAME, new Date(), metaObject);
    }

    /**
     * 更新时的填充策略
     *
     * @param metaObject Clinton Begin
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(UPDATE_TIME_FIELD_NAME, new Date(), metaObject);
    }
}
