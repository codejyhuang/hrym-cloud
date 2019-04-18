package com.everg.hrym.server.zuul.filter;

import com.everg.hrym.server.zuul.exception.ZuulBizException;
import com.everg.hrym.server.zuul.exception.ZuulResultCode;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * Created by hong on 2019/4/1.
 */
public class PostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {

        RequestContext ctx = RequestContext.getCurrentContext();
        if(ctx.getThrowable()!=null) {
            System.out.println("1111");
            return true;
        }

        System.out.println("22222");
        return false;
    }

    @Override
    public Object run() throws ZuulException {
//        throw new ZuulBizException(ZuulResultCode.COMMON_FAIL_ERROR);
        return null;
    }
}
