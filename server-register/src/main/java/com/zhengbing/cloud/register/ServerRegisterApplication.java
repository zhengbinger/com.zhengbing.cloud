package com.zhengbing.cloud.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * to see the document of official
 * can kown that,the eureka 2.0 was Discontinued to open source
 * but they also said "Eureka 1.x is a core part of Netflix's service discovery system and is still an active project."
 * so we can change the depency to eureka 1.x to resolve the question of eureka 2.0 discontinued to open source
 * </p>
 * the core work with register-center  add annotation EnableEurekaServer and configuration application.yml
 *
 * @author zhengbing
 */
@RestController
@EnableEurekaServer
@SpringBootApplication
public class ServerRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerRegisterApplication.class, args);
    }

    @GetMapping("/hello")
    public String index(){
        return "hello, zhengbing";
    }

}
