package com.everg.hrym.server.zuul.exception;

import com.everg.hrym.common.core.exception.HrymException;
import com.everg.hrym.common.core.exception.ResultCode;
import com.netflix.zuul.exception.ZuulException;

/**
 * Created by hong on 2019/4/1.
 */
public class ZuulBizException extends HrymException {

    public ZuulBizException(ResultCode code){
        super(code);
    }
}
