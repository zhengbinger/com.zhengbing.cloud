package com.zhengbing.cloud.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * to see the document of official
 * can see that,the eureka 2.0 was Discontinued to open source
 * but they also said "Eureka 1.x is a core part of Netflix's service discovery system and is still an active project."
 * so we can change the depency to eureka 1.x to resolve the question of eureka 2.0 discontinued to open source
 * <p></p>
 * register-center core work is add  annotation EnableEurekaServer and configuration application.yml
 */
@EnableEurekaServer
@SpringBootApplication
public class RegisterCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterCenterApplication.class, args);
    }

}
