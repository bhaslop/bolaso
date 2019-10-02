package com.bolaso.controller;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.bolaso.configuration.SecurityConfiguration;
import com.bolaso.security.TokenAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationController controller;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    protected String login(final HttpServletRequest req) {
        String redirectUri = req.getScheme() + "://" + req.getServerName();

        if( ("http".equals(req.getScheme()) && req.getServerPort() != 80) || ("https".equals(req.getScheme()) && req.getServerPort() != 433)) {
             redirectUri += ":" + req.getServerPort();
        }

        redirectUri += "/callback";

        String authorizeUrl = controller.buildAuthorizeUrl(req, redirectUri)
            .withAudience(String.format("https://%s/userinfo", securityConfiguration.getDomain()))
            .withScope("openid profile email")
            .build();

        return "redirect:" + authorizeUrl;
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    protected void getCallback(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        handleCallback(req, res);
    }

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    protected void postCallback(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        handleCallback(req, res);
    }

    private void handleCallback(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Tokens tokens = controller.handle(req);
            TokenAuthentication tokenAuth = new TokenAuthentication(JWT.decode(tokens.getIdToken()));

            SecurityContextHolder.getContext().setAuthentication(tokenAuth);
            res.sendRedirect("/");

        } catch (AuthenticationException | IdentityVerificationException e) {
            e.printStackTrace();

            SecurityContextHolder.clearContext();
            res.sendRedirect("/login");
        }
    }
}
