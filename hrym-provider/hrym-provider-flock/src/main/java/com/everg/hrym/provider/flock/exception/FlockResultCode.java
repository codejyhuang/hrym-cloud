package com.everg.hrym.provider.flock.exception;

import com.everg.hrym.common.core.exception.ResultCode;
import com.everg.hrym.common.core.exception.ResultCodeType;

public enum FlockResultCode implements ResultCode {

    ERROR1("1","用户已经加入共修群.请勿重复操作"),
    ERROR2("2","此共修群已解散"),
    ERROR3("3","不可删除全部群功课"),
    ERROR4("4","该功课已经被删除,请重新选择");


    private FlockResultCode(String code, String desc) {
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