package com.zhengbing.cloud.configurecenter.controller;

import com.zhengbing.cloud.configurecenter.config.GetConfig;
import com.zhengbing.cloud.configurecenter.config.GetConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.RespectBinding;

/**
 * @author zhengbing_vendor
 * @date 2020/6/4
 **/
@RestController
public class GitController {

    @Autowired
    private GetConfig getConfig;

    @Autowired
    private GetConfigProperties getConfigProperties;

    @GetMapping("config")
    public String config(){
        return getConfig.getInfo();
    }

    @GetMapping("properties")
    public String properties(){
        return getConfigProperties.getInfo();
    }
}
