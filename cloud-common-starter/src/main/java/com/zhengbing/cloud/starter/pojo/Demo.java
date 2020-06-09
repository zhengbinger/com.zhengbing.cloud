package com.zhengbing.cloud.starter.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhengbing
 * @date: 2020/6/7
 * @email: mydreambing@126.com
 */
@Data
public class Demo implements Serializable {

    private String name;
    private String type;
    private String roles;

}
