package com.zhengbing.cloud.configurecenter.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhengbing_vendor
 * @date 2020/6/4
 **/

@Data
@Component
public class GetConfig {

    @Value("${config.info}")
    private String info;
}
