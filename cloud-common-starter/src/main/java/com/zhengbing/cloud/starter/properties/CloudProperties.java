package com.zhengbing.cloud.starter.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定配置属性
 *
 * @author: zhengbing
 * @date: 2020/6/7
 * @email: mydreambing@126.com
 */
@Data
@ConfigurationProperties(prefix = "cloud.zhengbing")
public class CloudProperties {

    public static final String NAME = "郑冰";
    public static final String TYPE = "com.zhengbing";
    public static final String ROLES = "ADMIN";

    private String name;
    private String type;
    private String roles;

}
