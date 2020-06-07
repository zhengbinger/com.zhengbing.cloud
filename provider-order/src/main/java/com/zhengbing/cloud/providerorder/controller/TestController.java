package com.zhengbing.cloud.providerorder.controller;

import com.zhengbing.cloud.starter.pojo.Demo;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Demo demo;


    @GetMapping("/demo")
    public String demo() {
        System.out.println(demo.toString());
        return demo.toString();
    }
}
