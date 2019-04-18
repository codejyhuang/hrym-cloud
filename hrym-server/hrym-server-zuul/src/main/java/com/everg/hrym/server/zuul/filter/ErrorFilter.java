package com.everg.hrym.server.zuul.filter;

import com.everg.hrym.common.core.exception.CommonResultCode;
import com.everg.hrym.common.core.exception.HrymException;
import com.everg.hrym.common.core.exception.ResultCode;
import com.everg.hrym.server.zuul.exception.ZuulResultCode;
import com.everg.hrym.common.core.entity.BaseResult;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hong on 2019/4/1.
 */
public class ErrorFilter extends ZuulFilter {

    Logger slflogger = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        slflogger.info("error过滤器");
        BaseResult commonResult;
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Throwable throwable = ctx.getThrowable();
        if (throwable instanceof HrymException) {

            ResultCode resultCode = ((HrymException) throwable).getResultCode();
            ctx.setResponseStatusCode(200);
            commonResult = new BaseResult(resultCode.getCode(),resultCode.getDesc(),null);

            slflogger.info("捕获应用异常 request={} exception={}",request.getServletPath(),resultCode.getCode()+"|"+resultCode.getDesc());
            slflogger.error("业务员异常 耗时="+ "request="+request.getServletPath(), throwable);
        }else if (throwable instanceof ZuulException) {

            ctx.setResponseStatusCode(401);
            commonResult = new BaseResult(ZuulResultCode.COMMON_FAIL_ERROR.getCode(),ZuulResultCode.COMMON_FAIL_ERROR.getDesc(),null);
            slflogger.error("网关异常 "+" request="+request.getServletPath(), throwable);
        }else {

            ctx.setResponseStatusCode(401);
            commonResult = new BaseResult(CommonResultCode.CODE3001.getCode(),CommonResultCode.CODE3001.getDesc(),null);
            slflogger.error("未知异常 "+ "request="+request.getServletPath(), throwable);
        }
//        ctx.set("preReturnObject", commonResult);
        ctx.setResponseBody(commonResult.toString());
        ctx.remove("throwable");
        return null;
    }
}
