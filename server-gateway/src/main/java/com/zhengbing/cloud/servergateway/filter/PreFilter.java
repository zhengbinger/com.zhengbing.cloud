package com.zhengbing.cloud.servergateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @author zhengbing
 * @date 2019-08-17
 * @email mydreambing@126.com
 * @since version 1.0
 */

@Slf4j
@Component
public class PreFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
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
    public Object run() throws ZuulException {
        //获取当前请求的请求上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        //记录请求进入时间
        requestContext.set("api_request_time", System.currentTimeMillis());
        return null;
    }
}
