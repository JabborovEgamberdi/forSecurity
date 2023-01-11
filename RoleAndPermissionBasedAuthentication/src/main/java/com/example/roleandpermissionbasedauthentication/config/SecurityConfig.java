package com.example.roleandpermissionbasedauthentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // InMemory saved users
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                //for role based authorization
                .withUser("director1").password(passwordEncoder().encode("director1")).roles("DIRECTOR")
                //for permission based authorization
                .authorities("READ_ALL_PRODUCT", "EDIT_PRODUCT", "ADD_PRODUCT", "READ_ONE_PRODUCT")
                .and()
                .withUser("director2").password(passwordEncoder().encode("director2")).roles("DIRECTOR")
                .authorities("READ_ALL_PRODUCT", "EDIT_PRODUCT", "ADD_PRODUCT", "DELETE_PRODUCT", "READ_ONE_PRODUCT")
                .and()
                .withUser("user").password(passwordEncoder().encode("user")).roles("USER");
    }

//==========================================================================================================//

//  Role Based Authorization
//  Role Based Authentication
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/product/*").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/api/product/**").hasAnyRole("MANAGER", "DIRECTOR")
//                .antMatchers("/api/product/**").hasRole("DIRECTOR")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//    }

//==========================================================================================================//

//    Permission based Authorization
//    Permission based Authentication
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.DELETE, "/api/product/*").hasAuthority("DELETE_PRODUCT")
//                .antMatchers("/api/auth/**").hasAnyAuthority("READ_ALL_PRODUCT", "EDIT_PRODUCT", "ADD_PRODUCT", "DELETE_PRODUCT", "READ_ONE_PRODUCT")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//    }

    //  Basic Authentication
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}