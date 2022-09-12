package com.larbotech.saml2.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableWebSecurity
@Configuration
@ConditionalOnProperty(prefix = "spring.security", name = "oauth2.client.registration.wso2.client-secret")
public class Oauth2Config {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()

                // allow anonymous access to the root page
                .antMatchers("/").permitAll()

                // all other requests
                .anyRequest().authenticated()

                // 	Replace with logoutSuccessHandler(oidcLogoutSuccessHandler()) to support OIDC RP-initiated logout
                .and().logout().logoutSuccessHandler(oidcLogoutSuccessHandler())

                // enable OAuth2/OIDC
                .and().oauth2Login();

        return http.build();
    }

    //Inject the ClientRegistrationRepository which stores client registration information
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    /**
     * Create a OidcClientInitiatedLogoutSuccessHandler
     *
     * @return OidcClientInitiatedLogoutSuccessHandler
     */
    private LogoutSuccessHandler oidcLogoutSuccessHandler() {

        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedLogoutSuccessHandler(
                        this.clientRegistrationRepository);

        oidcLogoutSuccessHandler.setPostLogoutRedirectUri(
                "http://localhost:8080"); //Need to give the post-rediret-uri here

        return oidcLogoutSuccessHandler;
    }
}
