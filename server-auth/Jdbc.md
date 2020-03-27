# 统一认证中心 DB模式

## 改造统一认证中心

其实前面基础版本里面已经加上了，但是没有用到，这边把相关添加依赖的代码贴出来
1. 修改pom.xml添加依赖
```
<dependency>
   <groupId>mysql</groupId>
   <artifactId>mysql-connector-java</artifactId>
   <scope>runtime</scope>
</dependency>

<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```
2. 添加数据库相关的配置
由于系统架构中设计了配置中心，此处将数据库的相关配置文件在配置中心添加，配置中心默认采用的是native模式，
所以在config-center的main/resources/目录下新建了一个config目录来存放配置文件 auth-center-dev.yml
```
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/auth-center?nullCatalogMeansCurrent=true&autoReconnect=true&serverTimezone=Asia/Shanghai
    username: root
    password: root
  jpa:
    show-sql: true
```
3. 修改AuthorizationServerConfig类
添加注入
```
    @Autowired
    private DataSource dataSource;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthUserDetailService userDetailService;

    @Autowired
    private ClientDetailsService clientDetailsService;
```
将tokenStore 更换为使用  jdbcTokenStore
```
//    @Bean
//    public RedisTokenStore tokenStore() {
//        return new RedisTokenStore(connectionFactory);
//    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
```
添加 ClientDetails实现声明
```
    @Bean
    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }
```
修改public void configure(ClientDetailsServiceConfigurer clients) throws Exception 去掉写死的方式，采用JDBC数据源的形式，
```
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // 配置两个客户端，一个用于password认证一个用于client认证  存放于内存
//        String finalSecrety = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");
//        clients.inMemory()
//                .withClient("client")
//                .resourceIds(Utils.RESOURCEIDS.ORDER)
//                .secret(finalSecrety)
//                .authorizedGrantTypes("authorization_code", "refresh_token")
//                .authorities("oauth2")
//                .accessTokenValiditySeconds(3600)
//                .scopes("select")
//                .and()
//                .withClient("zhengbing")
//                .resourceIds(Utils.RESOURCEIDS.ORDER)
//                .secret(finalSecrety)
//                .authorities("oauth2")
//                .authorizedGrantTypes("password", "refresh_token")
//                .accessTokenValiditySeconds(3600)
//                .scopes("server");

        // 使用上文中注入的ClientDetailService 采用的是JDBC的数据源
        clients.withClientDetails(clientDetailsService());
}
```

修改 public void configure(AuthorizationServerEndpointsConfigurer endpoints) 
```
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // redisTokenStore
        // 调用此方法才能支持 password 模式。
//        endpoints.authenticationManager(authenticationManager)
//                // 设置用户验证服务
//                .tokenStore(tokenStore())
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        // db模式
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailService);
    }
```
添加用户实体类（AuthUser）
```
package com.zhengbing.cloud.authcenter.entity;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "auth_user")
public class AuthUser implements UserDetails, Serializable {
    private static final long serialVersionUID = -4122033668567546816L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<Role> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
```
添加角色实体类
```
package com.zhengbing.cloud.authcenter.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zhengbing
 */
@Data
@Entity
@Table(name = "user_role")
public class Role implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = -2432805731609094372L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
```
添加用户数据持久化接口类
```
package com.zhengbing.cloud.authcenter.repository;

import com.zhengbing.cloud.authcenter.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AuthUser, Long> {

    AuthUser findByUsername(String username);
}

```
添加用户具体服务实现
```
package com.zhengbing.cloud.authcenter.service;

import com.zhengbing.cloud.authcenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class AuthUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
```
改造 WebSecurityConfig 删除userDetailsService方法
```
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String finalPassword = "{bcrypt}" + bCryptPasswordEncoder.encode("123456");
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user_1").password(finalPassword).authorities("USER").build());
        manager.createUser(User.withUsername("user_2").password(finalPassword).authorities("USER").build());

        return manager;
    }
```
服务端改造至此就完成了
但是，这时候启动auth-center 会启动不了，报错内容如下
```
***************************
APPLICATION FAILED TO START
***************************

Description:

The bean 'clientDetailsService', defined in class path resource [com/zhengbing/cloud/authcenter/config/AuthorizationServerConfig.class], could not be registered. A bean with that name has already been defined in BeanDefinition defined in class path resource [org/springframework/security/oauth2/config/annotation/configuration/ClientDetailsServiceConfiguration.class] and overriding is disabled.

Action:

Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true

Disconnected from the target VM, address: '127.0.0.1:63644', transport: 'socket'

Process finished with exit code 0
```
为什么呢？
> 至于为什么?待后续去研究
怎么处理呢？
解决方案就是在，配置文件中加入一行配置
```
spring.main.allow-bean-definition-overriding=true
```
处理完之后就可以正常启动auth-center了，然后可以继续后面的工作，改造资源服务器

# 改造资源服务器 provider-user
1. 第一步同样，先加入依赖引用
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
```
1.1 第二步，配置数据源相关的配置，放到配置中心去，在config-center的resources/config/目录下添加provider的配置文件 provider-user-dev.yml
加入如下内容
```
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/auth-center?nullCatalogMeansCurrent=true&autoReconnect=true&serverTimezone=Asia/Shanghai
    username: root
    password: root
  jpa:
    show-sql: true
    generate-ddl: true
    hibernate:
      ddl-auto: update
```
1.2 因为之前的redis版本资源服务器的配置没有加入到config-center 所以现在要在把provider-user的配置跟config-center关联起来，在bootstrap.yml 中加入
```
  profiles:
    active: dev
  application:
    name: auth-center
  cloud:
    config:
      uri: http://localhost:11000/
```
这样，当我们采用dev环境的时候就会去配置中心去获取配置了

2. 将之前在认证服务器中写的  实体类可以拷贝过来

3. 在控制器Controller中加入一个用户注册的接口
