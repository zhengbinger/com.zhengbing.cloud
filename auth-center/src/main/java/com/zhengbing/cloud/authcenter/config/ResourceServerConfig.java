//package com.zhengbing.cloud.authcenter.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 资源服务器配置
// *
// * @author zhengbing_vendor
// * @ EnableResourceServer 启动资源服务
// * @date 2020/3/16
// **/
//
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.requestMatcher(new OAuth2RequestedMacher())
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .anyRequest().authenticated();
//    }
//
//    /**
//     * 定义OAuth2请求匹配器
//     */
//    private class OAuth2RequestedMacher implements RequestMatcher {
//        @Override
//        public boolean matches(HttpServletRequest request) {
//            String auth = request.getHeader("Authorization");
//            //判断来源请求是否包含oauth2授权信息,这里授权信息来源可能是头部的Authorization值以Bearer开头,
//            // 或者是请求参数中包含access_token参数,满足其中一个则匹配成功
//            boolean hasAuth2Token = (null != auth) && auth.startsWith("Bearer");
//            boolean haveAccessToken = null != request.getParameter("access_token");
//            return hasAuth2Token || haveAccessToken;
//        }
//    }
//}
