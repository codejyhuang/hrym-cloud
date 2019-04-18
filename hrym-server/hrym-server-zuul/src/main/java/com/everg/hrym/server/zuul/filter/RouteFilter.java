package com.everg.hrym.server.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者: 王培
 * 时间: 2017-07-21 10:48
 * 描述：route过滤器
 */
public class RouteFilter extends ZuulFilter {
    Logger slflogger = LoggerFactory.getLogger(RouteFilter.class);
    @Override
    public String filterType() {
        return "route";
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
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(200);
        slflogger.info("route过滤器");
        return null;
    }
}
