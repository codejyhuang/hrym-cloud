package com.everg.hrym.provider.lesson.exception;

import com.everg.hrym.common.core.exception.ResultCode;
import com.everg.hrym.common.core.exception.ResultCodeType;

public enum LessonResultCode implements ResultCode {

    ERROR1("1","已经有您建立的同名功课");


    private LessonResultCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    @Override
    public ResultCodeType getType() {
        return ResultCodeType.TEST;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}