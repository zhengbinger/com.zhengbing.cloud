package com.zhengbing.cloud.configurecenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置中心
 *
 * @author zhengbing_vendor
 * @date 2019/9/4
 */
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class ServerConfigureApplication {

    public static void main(String[] args){
        SpringApplication.run(ServerConfigureApplication.class,args);
    }

}
