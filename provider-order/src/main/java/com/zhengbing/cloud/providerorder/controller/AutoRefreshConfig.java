package com.zhengbing.cloud.providerorder.controller;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 能够自动刷新配置的用法
 * @author zhengbing_vendor
 * @date 2020/6/15
 **/
@Data
@Component
@ConfigurationProperties(prefix = "data")
public class AutoRefreshConfig {

    private String name;
}
