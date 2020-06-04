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
spring-cloud-config 服务端配置中心分布式存储配置文件有四种方式
1. native       本地文件系统
2. git-native   本地文件系统，git形式   
3. git-remote   git远程仓库
4. subversion   svn仓库
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
----

# git形式的本地存储方式
git官方建议，使用本地文件系统进行git存储库仅用于测试。使用服务器在生产环境中托管配置库。
git本地仓库方式，需要将存储配置文件的目录进行git操作,如下：   
#git init   
#git add  *    
#git  commit -m '配置文件提交'

spring:
    cloud:
      config:
        server:
          git:
            uri: file:///D:/Java/config
---

# git 远程存储方式
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/zhengbinger/config-center
          search-paths: '{application}'
          username: zhengbinger
          password: ******

----
# svn 仓库存储
需要在工程中引入，svnkit的依赖
且必须显示声明spring.profiles.active=subversion不然还是用的git，svn默认的lable是trunk
<dependency>
	<groupId>org.tmatesoft.svnkit</groupId>
	<artifactId>svnkit</artifactId>
</dependency>
spring.profiles.active=subversion
spring.cloud.config.server.svn.uri=http://localhost/usvn/svn/datadev/docs/config/
spring.cloud.config.server.svn.username=zhengbing
spring.cloud.config.server.svn.password=xxxxxx
spring.cloud.config.server.svn.search-paths={application} #使用{application}占位符
spring.cloud.config.server.svn.default-label=trunk
spring.cloud.config.server.svn.basedir=/data #默认在系统临时目录下面，需要调整一下避免临时文件被系统自动清理
````

## 环境库
环境库的资源有三个变量参数化：
{application}映射到客户端的"spring.application.name"
{profile}映射到客户端上的"spring.profiles.active"（逗号分隔列表）
{label}这是一个服务器端功能，标记"版本"分支的一组配置文件,例如: dev/master 等

### 查看配置文件库中的文件
http://localhost:11000/server-configure/dev  -- 默认使用master分支，等同于  http://localhost:11000/server-configure/dev/master
http://localhost:11000/server-configure/dev/dev  -- 查看dev分支的配置文件

### 将配置中心的配置文件存放到 配置中心仓库




