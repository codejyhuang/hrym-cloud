package com.everg.hrym.server.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.post.SendResponseFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * pre过滤器
 *
 * @author 王培
 * @create 2017-05-16 14:52
 **/
public class PreFilter extends ZuulFilter {

    Logger slflogger = LoggerFactory.getLogger(PreFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        slflogger.info("pre过滤器");
        RequestContext ctx = RequestContext.getCurrentContext();
        doSomething();
//        try{
//
//        }catch (Exception e) {
//
//            ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            ctx.set("error.exception", e);
//            ctx.set("error.message", e.getCause());
////            ctx.setSendZuulResponse(false);
////            ctx.setResponseStatusCode(401);
////            ctx.setResponseBody("response pre error");
//
//        }

        return null;
    }

    private void doSomething() {
//        throw new RuntimeException("Exist some errors...");
    }
}
