package com.zhengbing.cloud.utils.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhengbing
 * @descript 工具starter 配置封装类
 * @date 2019-08-01
 * @email mydreambing@126.com
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
