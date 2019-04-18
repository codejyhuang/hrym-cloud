package com.everg.hrym.common.core.annotation;

public @interface ReturnVOField {

    /**
     * 原始名称
     *
     * @return
     */
    String name() default "";

    /**
     * 转换后字段名称
     *
     * @return
     */
    String display() default "";

    /**
     * 扩展信息
     *
     * @return
     */
    String extend() default "";

    /**
     * 格式化
     */
    String format() default "";

    /**
     * 默认值
     */
    String defaultValue() default "";
}