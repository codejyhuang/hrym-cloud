package com.everg.hrym.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReturnVO {
    /**
     * 需要转换的字段
     * @return
     */
    ReturnVOField[] value();

    Class<?> targetClass() default Map.class;

    String parent() default "";
}