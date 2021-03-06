package com.zhengbing.cloud.servergateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 访问日志记录器
 *
 * @author zhengbing
 * @date 2019-08-17
 * @email mydreambing@126.com
 * @since version 1.0
 */

@Slf4j
@Component
public class AccessLogFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        // 需要最后一个执行的filter
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        log.info("Request \"{}\" spent : {} seconds.", request.getRequestURI(),
                (System.currentTimeMillis() - Long.parseLong(requestContext.get("api_request_time").toString())) / 1000);
        return null;
    }
}
