package com.zhengbing.cloud.starter.service;

import com.zhengbing.cloud.starter.properties.CloudProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhengbing_vendor
 * @date 2020/6/8
 **/

@Data
@Component
public class HelloService {

    @Autowired
    private CloudProperties cloudProperties;

    public void sayHello(){
        System.out.println("hello"+cloudProperties.toString());
    }
}
