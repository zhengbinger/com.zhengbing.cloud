#OAuth2统一认证中心(基础版为缓存篇，数据保存在redis)

##服务端

###依赖引用
```
 <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-oauth2</artifactId>
 </dependency>
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-data-redis</artifactId>
 </dependency>
```
##认证服务（AuthorizationServer）配置
1. 继承 AuthorizationServerConfigurerAdapter
2. 启用认证服务端 @EnableAuthorizationServer 和 @configuration
3. 引入AuthenticationManager和RedisConnectionFactory
4. 实例化TokenStore放入容器
5. 重写配置方法
>public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception 
>public void configure(AuthorizationServerSecurityConfigurer security) throws Exception 
>public void configure(ClientDetailsServiceConfigurer clients) throws Exception 

## 应用WebSecurity配置
1. 继承 extends WebSecurityConfigurerAdapter 
2. 启用web安全配置 @EnableWebSecurity 和 @Configuration
3. 实例化密码加密器
4. 重写并注入相关配置方法及对象
>protected UserDetailsService userDetailsService()
>public AuthenticationManager authenticationManagerBean() throws Exception
>public void configure(HttpSecurity http) throws Exception 

## 启用应用资源服务端
在主类中添加@EnableResourceServer
    