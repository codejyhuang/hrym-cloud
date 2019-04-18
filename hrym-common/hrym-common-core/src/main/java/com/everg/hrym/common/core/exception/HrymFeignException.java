package com.everg.hrym.common.core.exception;

import feign.FeignException;

/**
 * 要feign处理过的异常，是provider里面抛出来的
 *
 * @auther hong
 * @create 2019-04-04 14:32
 */
public class HrymFeignException extends RuntimeException {

    private String errorCode;

    private String errorDesc;

    public HrymFeignException(String msg) {
        super(msg);
    }
    public HrymFeignException(String errorCode, String errorDesc) {
        super(constructMessage(errorCode,errorDesc));
        this.errorCode=errorCode;
        this.errorDesc=errorDesc;
    }

    public String getErrorCode() {
        return errorCode;
    }


    public String getErrorDesc() {
        return errorDesc;
    }

    public static String constructMessage(String resultCode, String message) {
        return resultCode + "," + message;
    }


}