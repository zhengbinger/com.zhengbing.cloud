# 整合服务注册与发现中心
整合到注册中心，以service-id进行调用，便于服务之间的调用，与服务高可用的建设

此处只记录客户端的相关配置
pom.xml 添加依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

修改配置文件bootstrap.yml
```
spring:
  cloud:
    config:
      discovery:
        enabled: true
        # 配置通过注册中心的service（service-id）来连接配置中心，从而获取配置
        service-id: server-configure
```

修改客户端启动类
添加@EnableDiscoveryClient注解
