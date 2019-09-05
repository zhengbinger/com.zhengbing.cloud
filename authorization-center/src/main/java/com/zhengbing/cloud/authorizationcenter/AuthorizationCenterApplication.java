package com.zhengbing.cloud.authorizationcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 认证服务器
 *
 * @author zhengbing_vendor
 * @date 2019/9/5
 */
@EnableDiscoveryClient
@SpringCloudApplication
public class AuthorizationCenterApplication{

    public static void main(String[] args){
        SpringApplication.run(AuthorizationCenterApplication.class,args);
    }

}
