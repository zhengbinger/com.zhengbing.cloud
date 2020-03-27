package com.zhengbing.cloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * OAuth2.0 认证授权服务中心
 * 启用注册服务实现
 * @author zhengbing
 */
@EnableDiscoveryClient
@EnableResourceServer
@SpringBootApplication
public class ServerAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerAuthApplication.class, args);
    }

}
