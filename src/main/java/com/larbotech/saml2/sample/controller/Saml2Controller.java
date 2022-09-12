package com.larbotech.saml2.sample.controller;

import com.larbotech.saml2.sample.util.XMLFormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ConditionalOnProperty(prefix = "spring.security", name = "saml2.relyingparty.registration.wso2.assertingparty.singlesignon.url")
@Controller
public class Saml2Controller {

    @GetMapping("/")
    public String getIndexPage(Model model) throws Exception {

        Saml2Authentication auth = (Saml2Authentication) SecurityContextHolder.getContext().getAuthentication();
        String samlResponse = XMLFormatterUtil.getPrettyString(auth.getSaml2Response(), 2);
        model.addAttribute("username", auth.getName());
        model.addAttribute("samlresponse", samlResponse);
        return "index";
    }

    @Autowired
    private RelyingPartyRegistrationRepository relyingParties;

    @GetMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String registrationId = "wso2";
        String registration = relyingParties.findByRegistrationId(registrationId).getAssertingPartyDetails().getSingleSignOnServiceLocation();
        response.sendRedirect("/saml2/authenticate/" + registrationId);
    }
}
