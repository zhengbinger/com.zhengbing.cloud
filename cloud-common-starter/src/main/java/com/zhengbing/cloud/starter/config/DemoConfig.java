package com.zhengbing.cloud.starter.config;

import com.zhengbing.cloud.starter.pojo.Demo;
import com.zhengbing.cloud.starter.properties.DemoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhengbing
 * @date: 2020/6/7
 * @email: mydreambing@126.com
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnClass(DemoProperties.class)
public class DemoConfig {

    @Autowired
    private DemoProperties demoProperties;

    @Bean
    Demo demo() {
        Demo demo = new Demo();
        demo.setName(demoProperties.getName() == null ? DemoProperties.NAME : demoProperties.getName());
        demo.setType(demoProperties.getType() == null ? DemoProperties.TYPE : demoProperties.getType());
        demo.setRoles(demoProperties.getRoles() == null ? DemoProperties.ROLES : demoProperties.getRoles());
        System.out.println(demo.getName());
        return demo;
    }
}
