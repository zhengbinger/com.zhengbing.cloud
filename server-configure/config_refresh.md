# 配置中心自动刷新配置(Actuator)
配置在客户端 provider-order
## 依赖引入
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
## 客户端调用配置文件配置方式
1. @Value("${data.name}") 此方式不支持自动刷新配置
2. @ConfigureProperties(prefix="data")

## 配置文件加入
以下配置，未弄清楚加入的作用及原理。后续学习
```
management:
  endpoint:
    shutdown:
      enabled: false
  endpoints:
    web:
      exposure:
        include: "*"

data:
  name: NaN
```

## 具体操作
定义两个接口，一个调用@Value注解获取配置文件中内容的方式/demo,另一个调用@ConfigureProperties注解获取配置文件内容的方式/demo/refresh   
```
@Data
@Component
public class GetConfigWithValue {
    @Value("${data.name}")
    private String demo;
}

@Data
@Component
@ConfigurationProperties(prefix = "data")
public class AutoRefreshConfig {
    private String name;
}

@RestController
@RefreshScope
public class TestController {

    @Autowired
    private GetConfigWithValue getConfigWithValue;

    @Autowired
    private AutoRefreshConfig autoRefreshConfig;

    @GetMapping("/demo")
    public String demo() {
        System.out.println(getConfigWithValue.getDemo());
        return getConfigWithValue.getDemo();
    }

    @GetMapping("/demo/refresh")
    public String demoRefresh() {
        return autoRefreshConfig.getName();
    }
}

```
启动配置中心与客户端，分别在浏览器调用两个不同方式获取配置文件内容的方式的接口 /demo   /demo/refresh   
浏览器中返回了相同的内容，zhengbing 

然后使用postman 调用  /actuator/refresh 接口，来刷新配置文件,
如果git上的配置文件没有发生修改,则返回[],否则返回如下相似内容：
```json
[
    "config.client.version",
    "data.name"
]
```

再刷新两个调用配置文件内容的浏览器页面，/demo/refresh接口返回在浏览器上的内容已经更新，/demo 返回的内容还是修改之前的配置文件中的内容


