package com.zhengbing.cloud.utils.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 工具starter 配置封装类
 * @author zhengbing
 * @date 2019-08-01
 * @since version 1.0
 */
@Data
@ConfigurationProperties(prefix = "cloud.utils")
public class UtilsProperties {

    /**
     * temporary path of file process
     */
    private String tempFilePath;

}
