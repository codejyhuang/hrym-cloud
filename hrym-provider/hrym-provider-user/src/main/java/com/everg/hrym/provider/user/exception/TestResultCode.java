package com.everg.hrym.provider.user.exception;

import com.everg.hrym.common.core.exception.ResultCode;
import com.everg.hrym.common.core.exception.ResultCodeType;

public enum TestResultCode implements ResultCode {

    ERROR1("Test10002","测试业务");
    private TestResultCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

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