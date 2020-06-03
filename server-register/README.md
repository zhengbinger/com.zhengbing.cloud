# 服务治理
## Spring Cloud Eureka实现服务治理
### 创建注册中心 (euraka server)
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

### 创建服务提供者(euraka client)
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
复制并修改注册中心配置文件 
fetch-registry:true  //从其他的服务中心同步服务列表
register-with-eureka:true // 把自己作为服务注册到其他服务注册中心
defaultZone: http://localhost:10001/eureka   // 除本身的注册中心之外的其他服务注册中心

```
server: 
    port: 10000

eureka:
  client:
    # 是否从其他的服务中心同步服务列表
    fetch-registry: true
    # 是否把自己作为服务注册到其他服务注册中心
    register-with-eureka: true
    service-url:
      defaultZone: http://localhost:10002/eureka,http://localhost:10003/eureka
```
启动多注册中心的方式： 
 
1. 打包运行方式：
将程序打包成jar文件，在执行运行程序命令的时候，添加--spring.profiles.active=peer

2. idea中运行
run-->edit configurations -->将注册中心的运行配置进行复制-->Copy configrations   
--> 修改 active profiles对应高可用节点的profiles名称即可

#### 修改Euraka Client 是服务连接到注册中心集群
修改eureka client 的配置文件,中的

***同时注册到两个注册中心***
eureka.client.service-url.defaultZone: http://localhost:10001/eureka/,http://localhost:10002/eureka/

启动即可注册到集群。中途如需测试HA的使用情况，可以中途停掉一个注册中心，查看客户端是否会转移到另一个注册中心

###给Eureka注册中心添加认证
在pom.xml中添加对spring-security的依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
修改配置文件，添加配置认证的用户名密码
```
spring.security.user.name=root
spring.security.user.password=root
```

添加Java配置WebSecurityConfig
默认情况下添加SpringSecurity依赖的应用每个请求都需要添加CSRF token才能访问，Eureka客户端注册时并不会添加，   
所以需要配置/eureka/**路径不需要CSRF token。



    
   
    
    


