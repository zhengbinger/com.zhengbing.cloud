package com.zhengbing.cloud.userprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户服务
 *
 * @author zhengbing_vendor
 * @date 2019/9/4
 */
@EnableDiscoveryClient
@SpringBootApplication
public class UserProviderApplication{

    public static void main(String[] args){
        SpringApplication.run(UserProviderApplication.class,args);
    }

}
