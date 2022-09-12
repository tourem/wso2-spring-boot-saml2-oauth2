package com.larbotech.saml2.sample.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@ConditionalOnProperty(prefix = "spring.security", name = "oauth2.client.registration.wso2.client-secret")
public class Oauth2Controller {

    @GetMapping("/")
    public String getProfile(Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            DefaultOidcUser userDetails = (DefaultOidcUser) authentication.getPrincipal();
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("userName", userDetails.getName());
            model.addAttribute("IDTokenClaims", userDetails);
            model.addAttribute("AccessToken", authentication.getCredentials());
        }
        return "home";
    }
}
