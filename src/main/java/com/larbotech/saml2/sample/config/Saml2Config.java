package com.larbotech.saml2.sample.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

@ConditionalOnProperty(prefix = "spring.security", name = "saml2.relyingparty.registration.wso2.assertingparty.singlesignon.url")
@EnableWebSecurity
public class Saml2Config {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests(authz -> authz
                .mvcMatchers("/login").permitAll() // here
                .mvcMatchers("/delete").hasAnyRole("delete") // here
                .anyRequest().permitAll())
                .saml2Login(saml2 -> saml2.loginPage("/login"));// and here

        return http.build();
    }

}
