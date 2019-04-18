package com.everg.hrym.common.core.exception;

/**
 * Created by hong on 2019/3/29.
 * hrym 业务异常类
 */
public class HrymException extends RuntimeException {

    /**
     * 异常码有全局的CommonResultCode和业务特有的
     */
    private ResultCode resultCode;

    private Object[] params;

    public HrymException(){

    }

    public HrymException(String message) {

        //没有异常码的默认传3001
        super(constructMessage(CommonResultCode.CODE3001.getType(),CommonResultCode.CODE3001.getCode(),message));
        resultCode = CommonResultCode.CODE3001;
    }

    public HrymException(String message, Throwable cause) {
        //没有异常码的默认传3001
        super(constructMessage(CommonResultCode.CODE3001.getType(),CommonResultCode.CODE3001.getCode(), message), cause);
        resultCode = CommonResultCode.CODE3001;
    }

    public HrymException(ResultCode resultCode, String message, Throwable cause) {
        super(constructMessage(resultCode.getType(),resultCode.getCode(), message), cause);
        this.resultCode = resultCode;
    }

    public HrymException(ResultCode resultCode, Object ...params) {
        super(constructMessage(resultCode.getType(),resultCode.getCode(),resultCode.getDesc()));
        this.resultCode = resultCode;
        this.params = params;
    }


    public static String constructMessage(ResultCodeType type, String resultCode, String message) {
        return type.getDesc()+","+resultCode + "," + message + "," + "HrymException";
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
