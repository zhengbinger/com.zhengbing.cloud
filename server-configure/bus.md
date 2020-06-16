# 使用 Spring Cloud Bus 来自动刷新多个端（spring-cloud-bus）
> 提前准备好 rabbitmq 的环境
1. 在客户端添加依赖
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```
2. 客户端添加配置
```
spring:
    rabbitmq:
        host: localhost
        port: 5672 
        username: guest
        password: guest
```

3. 启动两个或两个以上客户端应用
> 可以通过edit configuration -> copy configuration -> vm option -> -Dserver.port=9101来对同一个项目启动多个

4. 在浏览器分别打开每个客户端对应的  /demo/refresh ，获取配置文件中的数据
5. 修改git远程库中的配置文件内容
6. 重新访问每个客户端的/demo/refresh的接口，发现获取到的配置文件内容没有更新
7. 访问任意一个客户端的/actuator/bus-refresh接口，采用post方式，可以使用postman
> http://localhost:9101/actuator/bus-refresh
请求成功之后，各个客户端的终端控制台会输出：
```
Received remote refresh request. Keys refreshed [config.client.version, data.name]
``` 
8. 再次访问任意客户端的/demo/refresh的接口，发现获取到的配置文件内容已经更新