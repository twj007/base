package com.shiro.config;

import com.shiro.component.MyRealm;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/15
 **/
@Configuration
public class ShiroConfig {

    @Bean("myRealm")
    MyRealm myRealm(){
        return new MyRealm();
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        creator.setUsePrefix(true);
        return creator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

//    @Bean
//    public SecurityManager securityManager(){
//        SecurityManager manager = new DefaultSecurityManager();
//        ((DefaultSecurityManager) manager).setRealm(myRealm());
//        return manager;
//    }

    /***
     *
     * @return
     */
    @Bean
    ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition chain =  new DefaultShiroFilterChainDefinition();
//        chain.addPathDefinition("/", "anon");
//        chain.addPathDefinition("/static/**", "anon");
//        chain.addPathDefinition("/login", "anon");
//        chain.addPathDefinition("/register", "anon");
//        chain.addPathDefinition("/admin", "authc,roles[admin]");
//        chain.addPathDefinition("/**", "authc");
        return chain;
    }

    /***
     * 需要自定义连接器时通过这个去配置
     * @return
     */
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("webSecurityManager") DefaultWebSecurityManager webSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(webSecurityManager);
        //拦截器
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        // 添加自己的过滤器并且取名
//        Map<String, Filter> filterMap = new HashMap<>(16);
//        filterMap.put("my", new Filter() {
//            @Override
//            public void init(FilterConfig filterConfig) throws ServletException {
//
//            }
//
//            @Override
//            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//            }
//
//            @Override
//            public void destroy() {
//
//            }
//        });
//        shiroFilterFactoryBean.setFilters(filterMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    SimpleCookie simpleCookie(){
        SimpleCookie rememberMe = new SimpleCookie("remember");
        rememberMe.setMaxAge(60 * 60 * 60);
        return rememberMe;
    }

    @Bean
    CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCookie(simpleCookie());
        manager.setCipherKey(Base64.getDecoder().decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return manager;
    }

    @Bean("webSecurityManager")
    DefaultWebSecurityManager webSecurityManager(@Qualifier("myRealm") MyRealm myRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRememberMeManager(rememberMeManager());
        manager.setRealm(myRealm);
        return manager;
    }

}
