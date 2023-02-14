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

    private CustomUserDetailsService customUserDetailsService;
    private DataSource dataSource;
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, DataSource dataSource) {
        this.customUserDetailsService = customUserDetailsService;
        this.dataSource = dataSource;
    }

//    private DataSource dataSource;
//    public WebSecurityConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

//    private final SuccessUserHandler successUserHandler;
//    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
//        this.successUserHandler = successUserHandler;
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/index").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()//.successHandler(successUserHandler)
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(userService);
//        auth.setPasswordEncoder(passwordEncoder());
//        return auth;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//        auth.jdbcAuthentication()
//                        .dataSource(dataSource).passwordEncoder(passwordEncoder())
//                        .usersByUsernameQuery("select username, password, id from users_table where username=?", userService.findByUsername(username))
//                                .authoritiesByUsernameQuery("SELECT * FROM users_table ut JOIN users_roles ur ON (ut.id = ur.user_id) JOIN roles ON (ur.role_id = roles.id)");
//        auth.authenticationProvider(daoAuthenticationProvider());
    }

    /*

SELECT * FROM users_table ut
    JOIN users_roles ur ON (ut.id = ur.user_id)
    JOIN roles ON (ur.role_id = roles.id);

SELECT role_id FROM users_roles
    WHERE role_id IN (SELECT user_id FROM users_table);

INSERT INTO users_roles
VALUES ((SELECT MAX(id) FROM users_table ), 2);

    -- configure --

    // аутентификация inMemory
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
    */
}