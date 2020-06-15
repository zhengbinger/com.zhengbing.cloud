package com.zhengbing.cloud.providerorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhengbing
 * @date: 2020/6/7
 * @email: mydreambing@126.com
 */
@RestController
@RefreshScope
public class TestController {

    @Autowired
    private GetConfigWithValue getConfigWithValue;


    @Autowired
    private AutoRefreshConfig autoRefreshConfig;

    @GetMapping("/demo")
    public String demo() {
        System.out.println(getConfigWithValue.getDemo());
        return getConfigWithValue.getDemo();
    }

    @GetMapping("/demo/refresh")
    public String demoRefresh() {
        return autoRefreshConfig.getName();
    }
}
