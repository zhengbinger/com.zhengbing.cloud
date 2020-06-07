package com.zhengbing.cloud.starter.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: zhengbing
 * @date: 2020/6/7
 * @email: mydreambing@126.com
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cloud.zhengbing")
public class DemoProperties {

    public static final String NAME = "郑冰";
    public static final String TYPE = "com.zhengbing";
    public static final String ROLES = "ADMIN";

    private String name;
    private String type;
    private String roles;


}
