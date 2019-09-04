package com.zhengbing.cloud.missioncenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 任务处理中心
 *
 * @author zhengbing_vendor
 * @date 2019/9/4
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MissionCenterApplication{

    public static void main(String[] args){
        SpringApplication.run(MissionCenterApplication.class,args);
    }

}
