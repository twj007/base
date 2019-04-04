package com.framework.file.config;

import com.framework.file.component.GoAuthenticationFailureHandler;
import com.framework.file.component.GoAuthenticationSuccessHandler;
import com.framework.file.component.GoLogoutSuccessHandler;
import com.framework.file.dao.user.UserDao;
import com.framework.file.pojo.user.SysUser;
import com.framework.file.pojo.user.User;
import com.framework.file.pojo.user.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.web.context.request.RequestContextHolder;

import javax.sql.DataSource;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource dataSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.headers()
                    .frameOptions()
                    .disable()
                .and()
                .authorizeRequests()
                    .antMatchers(
            "/",
                        "/webjars/**",
                        "/index",
                        "/user/toRegister",
                        "/user/register",
                        "/user/login",
                        "/error",
                        "/user/checkUserExists",
                        "/static/**",
                        "/templates/**",
                        "/upload",
                        "/toUpload",
                        "/user/batchRegister")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                .formLogin()
                    .loginPage("/user/toLogin")
                    .successHandler(new GoAuthenticationSuccessHandler())
                    .failureHandler(new GoAuthenticationFailureHandler())
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/user/logout")
                    .invalidateHttpSession(true)
                    .logoutSuccessHandler(new GoLogoutSuccessHandler())
                    .permitAll()
                .and()
                .rememberMe()
                    .rememberMeCookieName("remember")
                    .tokenValiditySeconds(60)
                    .userDetailsService(userDetailsService())
                    .tokenRepository(tokenRepository())
                .and()
                .csrf()
                    .disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        tokenRepository.setCreateTableOnStartup(false);
        return tokenRepository;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService(){
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                SysUser u  = userDao.authUser(s);
                if(u == null){
                    throw new RuntimeException("用户名密码错误");
                }
                if("1".equals(u.getStatus())){
                    throw new RuntimeException("该用户已被禁用");
                }
                return new UserDetail(u);
            }
        };
    }
}