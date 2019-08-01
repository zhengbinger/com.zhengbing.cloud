package com.zhengbing.cloud.utils.configure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhengbing
 * @descript 工具包starter核心控制类
 * @date 2019-08-01
 * @email mydreambing@126.com
 * @since version 1.0
 */
@Configuration
@EnableConfigurationProperties(UtilsProperties.class)
@ConditionalOnProperty(prefix = "cloud.utils", value = "enable", matchIfMissing = true)
public class UtilsAutoConfigure {

}
