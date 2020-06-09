package com.zhengbing.cloud.providerorder.controller;

import com.zhengbing.cloud.starter.pojo.Demo;
import com.zhengbing.cloud.starter.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhengbing
 * @date: 2020/6/7
 * @email: mydreambing@126.com
 */
@RestController
public class TestController {

    @Autowired
    private TestComponent testComponent;

    @GetMapping("/demo")
    public String demo() {
        System.out.println(testComponent.getDemo());
        return testComponent.getDemo();
    }
}
