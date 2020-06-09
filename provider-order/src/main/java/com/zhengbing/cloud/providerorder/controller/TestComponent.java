package com.zhengbing.cloud.providerorder.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhengbing_vendor
 * @date 2020/6/9
 **/
@Data
@Component
public class TestComponent {

    @Value("${name}")
    private String demo;

    public String getDemo() {
        return demo;
    }
}
