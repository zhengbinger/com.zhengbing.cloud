package com.zhengbing.cloud.gatewaycenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;


@EnableZuulServer
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayCenterApplication {

    public static void main ( String[] args ) {
        SpringApplication.run( GatewayCenterApplication.class, args );
    }

}
