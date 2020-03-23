package com.zhengbing.cloud.authcenter.config;

import com.zhengbing.cloud.authcenter.service.AuthUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * /*
 * [/oauth/authorize]
 * [/oauth/token]
 * [/oauth/check_token]
 * [/oauth/confirm_access]
 * [/oauth/token_key]
 * [/oauth/error]
 * 认证服务中心服务核心配置
 *
 * @author zhengbing_vendor
 * @date 2020/3/16
 **/

@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthUserDetailService userDetailService;

    @Autowired
    private ClientDetailsService clientDetailsService;

//    @Bean
//    public RedisTokenStore tokenStore() {
//        return new RedisTokenStore(connectionFactory);
//    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 声明 ClientDetails实现
     *
     * @return
     */
    @Bean
    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 用来配置客户端详情服务
     * 客户端详情信息在这里进行初始化，能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息，
     * 一般使用数据库来存储或读取应用配置的详情信息（client_id ，client_secret，redirect_uri 等配置信息）
     * 在这里定义各个端的约束条件。
     * ClientId、Client-Secret：这两个参数对应请求端定义的 cleint-id 和 client-secret
     * <p>
     * authorizedGrantTypes 可以包括如下几种设置中的一种或多种：
     * 1. authorization_code：授权码类型。
     * 2. implicit：隐式授权类型。
     * 3. password：资源所有者（即用户）密码类型。
     * 4. client_credentials：客户端凭据（客户端ID以及Key）类型。
     * 5.refresh_token：通过以上授权获得的刷新令牌来获取新的令牌
     * <p>
     * accessTokenValiditySeconds：token 的有效期
     * scopes：用来限制客户端访问的权限，在换取的 token 的时候会带上 scope 参数，只有在 scopes 定义内的，才可以正常换取 token。
     *
     * @param clients ClientDetailsServiceConfigurer
     * @throws Exception Exception
     */
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

    /**
     * 用来配置授权以及令牌（Token）的访问端点和令牌服务（比如：配置令牌的签名与存储方式）
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)，还有token的存储方式(tokenStore)；
     *
     * @param endpoints AuthorizationServerEndpointsConfigurer
     * @throws Exception Exception
     */
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

    /**
     * 用来配置令牌端点(Token Endpoint)的安全与权限访问。
     * <p>
     * 用来配置令牌端点(Token Endpoint)的安全约束；
     *
     * @param security AuthorizationServerSecurityConfigurer
     * @throws Exception Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许客户端表单验证访问 OAuth2 授权接口，否则请求 token 会返回 401
        security.allowFormAuthenticationForClients()
                // 允许已授权用户访问 checkToken 接口
                .checkTokenAccess("isAuthenticated()")
                // 获取 token 接口
                .tokenKeyAccess("permitAll()");
    }
}
