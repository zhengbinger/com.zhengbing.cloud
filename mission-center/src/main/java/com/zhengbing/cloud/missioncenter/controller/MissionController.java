package com.zhengbing.cloud.missioncenter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zhengbing_vendor
 * @date: 2019/8/1
 */
@RestController
public class MissionController {

    @RequestMapping("/")
    public String test(){
        System.out.println("china is no1");
        return "china is no 1";
    }
}
