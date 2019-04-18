package com.everg.hrym.common.core.exception;

import org.apache.commons.lang.StringUtils;

/**
 * Created by hong on 2019/4/1.
 */
public enum ResultCodeType {

    FLOCK("FLOCK","群"),
    LESSON("LESSON","功课"),
    USER("USER","用户"),
    SYSTEM("SYSTEM", "系统通用"),
    ZUUL("ZUUL", "网关"),
    TEST("TEST", "测试"),
    ;


    private ResultCodeType(String code, String desc) {

        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过name获取结果码
     *
     * @param code  错误码
     * @return      返回业务结果码
     */
    public static ResultCodeType getTypeEnum(String code) {
        for (ResultCodeType type : values()) {
            if (StringUtils.equals(type.getCode(), code)) {
                return type;
            }
        }

        return null;
    }

    /** 结果类型code，将作为结果码的前缀 */
    private String code;

    /** 描述 */
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
