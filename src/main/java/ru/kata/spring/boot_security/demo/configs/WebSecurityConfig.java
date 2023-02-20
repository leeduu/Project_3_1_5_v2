package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.CustomUserDetailsService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.sql.DataSource;
import javax.xml.crypto.dom.DOMCryptoContext;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private SuccessUserHandler successUserHandler;
    private CustomUserDetailsService customUserDetailsService;
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, SuccessUserHandler successUserHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.successUserHandler = successUserHandler;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "/index", "/login").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
            .anyRequest().authenticated()
            .and()
            .formLogin().successHandler(successUserHandler)
            .permitAll()
            .and()
//            .exceptionHandling().accessDeniedPage("/error")
//            .and()
            .logout()
            .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {  // существует ли юзер...
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        authenticationProvider.setUserDetailsService(customUserDetailsService);  // ...если да - положить в Spring Security Context
//        return authenticationProvider;
//    }
}