# 微服务配置中心
## Spring Cloud Config 实现分布式配置
### 准备配置仓库
    本人以相对路径（当前项目工程路径下的本地文件系统为例）
    classpath:/config
### 创建配置中心
#### 步骤一：加入依赖
创建一个基础的Spring Boot工程，并在pom.xml中引入依赖
````
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>
</dependencies>
````
#### 步骤二：在程序主类上添加@EnableConfigServer注解，开启spring cloud config的服务端功能
````
@EnableConfigServer
@SpringBootApplication
public class ConfigureCenterApplication{

    public static void main(String[] args){
        SpringApplication.run(ConfigureCenterApplication.class,args);
    }

}
````
#### 步骤三：配置服务端
````
server:
  port: 11000

eureka:
  client:
    service-url:
      defaultZone: http://localhost:10009/eureka/

# 本地文件存储方式
spring:
  application:
    name: @pom.artifactId@
  profiles:
    active: native
  cloud:
    config:
      server:
        native:
          search-locations: classpath:/config
~~~ 
# git 存储方式
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/zhengbinger/config-center
          search-paths: '{application}'
          username: zhengbinger
          password: ******
````



