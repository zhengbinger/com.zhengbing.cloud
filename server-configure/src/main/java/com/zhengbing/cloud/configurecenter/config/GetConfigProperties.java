package com.zhengbing.cloud.configurecenter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhengbing_vendor
 * @date 2020/6/4
 **/
@Data
@Component
@ConfigurationProperties(prefix = "config")
public class GetConfigProperties {

    private String info;
}
