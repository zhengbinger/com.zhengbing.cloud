package com.zhengbing.cloud.starter.config;

import com.zhengbing.cloud.starter.pojo.Demo;
import com.zhengbing.cloud.starter.properties.CloudProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 相关starter业务核心配置
 *
 * @author: zhengbing
 * @date: 2020/6/7
 * @email: mydreambing@126.com
 */
@Configuration
@ConditionalOnClass(CloudProperties.class)
@EnableConfigurationProperties(CloudProperties.class)
public class CloudConfigure {

    @Autowired
    private CloudProperties cloudProperties;

    @Bean
    Demo demo() {
        Demo demo = new Demo();
        demo.setName(cloudProperties.getName() == null ? CloudProperties.NAME : cloudProperties.getName());
        demo.setType(cloudProperties.getType() == null ? CloudProperties.TYPE : cloudProperties.getType());
        demo.setRoles(cloudProperties.getRoles() == null ? CloudProperties.ROLES : cloudProperties.getRoles());
        System.out.println(demo.getName());
        return demo;
    }
}
