package com.zhengbing.cloud.missioncenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zhengbing_vendor
 * @date: 2019/8/1
 */
@RestController
public class MissionController{

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/test")
    public String test(){
        String service="services "+discoveryClient.getServices();
        System.out.println(service);
        return service;
    }
}
