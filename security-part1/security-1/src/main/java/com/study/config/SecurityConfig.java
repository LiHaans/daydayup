package com.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;

/**
 * @Auther: lihang
 * @Date: 2021-06-22 21:40
 * @Description:
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private PersistentTokenRepository persistentTokenRepository;

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //http.csrf().disable();

        http.exceptionHandling().accessDeniedPage("/unauth");
        http.formLogin() // 表单登录
                .loginPage("/index")
                .loginProcessingUrl("/login")
                .successForwardUrl("/success");
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/index").permitAll();

        http.rememberMe()
                .tokenValiditySeconds(1000*60)
                .tokenRepository(persistentTokenRepository)
                .userDetailsService(userDetailsService);

        http.authorizeRequests()
                .antMatchers("/index")
                .permitAll()
                /*.antMatchers("/test1").hasRole("管理员")
                .antMatchers("/test2").hasAuthority("menu:user")*/
                .anyRequest()
                .authenticated();



    }
}
