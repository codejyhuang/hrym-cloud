package com.everg.hrym.server.zuul.exception;

import com.everg.hrym.common.core.exception.ResultCode;
import com.everg.hrym.common.core.exception.ResultCodeType;
import org.apache.commons.lang.StringUtils;

/**
 * Created by hong on 2019/4/1.
 */
public enum ZuulResultCode implements ResultCode {

    COMMON_FAIL_ERROR("-1","网关异常"),
    SUCCESS("0", "处理成功");

    private ZuulResultCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过name获取结果码
     *
     * @param code  错误码
     * @return      返回业务结果码
     */
    public static ZuulResultCode getResultEnum(String code) {
        for (ZuulResultCode result : values()) {
            if (StringUtils.equals(result.getCode(), code)) {
                return result;
            }
        }
        return null;
    }

    private String code;
    private String desc;

    @Override
    public ResultCodeType getType() {
        return ResultCodeType.ZUUL;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
