package com.everg.hrym.common.core.entity;

import com.everg.hrym.common.core.exception.CommonResultCode;
import com.everg.hrym.common.core.exception.HrymException;
import com.everg.hrym.common.core.exception.ResultCode;

import java.io.Serializable;

/**
 * 统一返回结果类
 * Created by qhzhang on 2017/2/18.
 */
public class BaseResult implements Serializable{

    // 状态码：0成功，其他为失败
    public String code = "0";

    // 成功为success，其他为失败原因
    public String message = "成功";

    // 数据结果集
    public Object data;

    public BaseResult() {
        this(CommonResultCode.CODE0000.getCode(), CommonResultCode.CODE0000.getDesc(),null);
    }

    public BaseResult(Object object) {
        this(CommonResultCode.CODE0000.getCode(),CommonResultCode.CODE0000.getDesc(),object);
    }

    public BaseResult(String code, String message) {
        this(code, message, null);
    }

    public BaseResult(HrymException ex) {
        this(ex.getResultCode().getCode(), ex.getResultCode().getDesc()+",HrymException", null);
    }

    public BaseResult(ResultCode code) {
        this(code.getCode(), code.getDesc(), null);
    }

    public BaseResult(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
