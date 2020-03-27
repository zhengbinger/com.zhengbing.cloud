# 服务治理
## Spring Cloud Eureka实现服务治理
### 创建注册中心
#### 步骤一：添加依赖
创建一个基础的Spring Boot工程，并在pom.xml中引入需要的依赖内容：
``````
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
    </dependencies>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
``````
#### 步骤二：注册服务注册中心
通过@EnableEurekaServer注解启动一个服务注册中心提供给其他应用进行对话。只需要在一个普通的Spring Boot应用中添加注解就能开启此功能，代码如下：
``````
@EnableEurekaServer
@SpringBootApplication
public class RegisterCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterCenterApplication.class, args);
    }
}
``````
#### 步骤三：配置application.yml 配置文件

``````
server:
  port: 10009

eureka:
  instance:
    hostname: localhost
  client:
    # 是否从其他的服务中心同步服务列表
    fetch-registry: false
    # 是否把自己作为服务注册到其他服务注册中心
    register-with-eureka: false

spring:
  application:
    name: @pom.artifactId@

``````
#### 步骤四：启动服务注册中心 

正常启动后，可以通过http://localhost:10009 访问服务注册中心页面

### 创建服务提供者
创建提供服务的客户端，并向服务注册中心注册。这里我们在服务提供者中尝试着提供一个接口来获取当前所有的服务信息        
#### 步骤一  加入依赖
创建一个Spring Boot应用，加入依赖：
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```
#### 步骤二：注册服务治理客户端
在工程主类中通过加上@EnableDiscoveryClient注解，该注解能激活Eureka中的DiscoveryClient实现
````
@EnableDiscoveryClient
@SpringBootApplication
public class MissionCenterApplication{

    public static void main(String[] args){
        SpringApplication.run(MissionCenterApplication.class,args);
    }

}
````
#### 步骤三：配置application.yml配置文件
````
server:
  port: 12100

eureka:
  client:
    service-url:
      defaultZone: http://localhost:10009/eureka/

spring:
  application:
    name: mission-center
````
#### 步骤四：写接口
实现请求处理接口，通过DiscoveryClient对象，在日志中打印出服务实例的相关内容。
````
@RequestMapping("/test")
public String test(){
    String service="services "+discoveryClient.getServices();
    System.out.println(service);
    return service;
}
````
#### 步骤五：启动服务提供者
启动工程，访问http://localhost:12100/test
页面返回打印出注册中心的所有服务

## 高可用配置

