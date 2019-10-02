package com.bolaso.controller;

import com.bolaso.configuration.SecurityConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LogoutController implements LogoutSuccessHandler {

    @Autowired
    SecurityConfiguration securityConfiguration;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onLogoutSuccess(HttpServletRequest req,
                                HttpServletResponse res,
                                Authentication authentication) {
        logger.debug("Performing loggout");

        invalidateSession(req);
        String returnTo = getReturnUrl(req);

        String logoutUrl = String.format(
            "https://%s/v2/logout?client_id=%s&returnTo=%s",
            securityConfiguration.getDomain(),
            securityConfiguration.getClientId(),
            returnTo);

        try {
            res.sendRedirect(logoutUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getReturnUrl(HttpServletRequest req) {
        String returnTo = req.getScheme() + "://" + req.getServerName();

        if ((req.getScheme().equals("http") && req.getServerPort() != 80) || (req.getScheme().equals("https") && req.getServerPort() != 443)) {
            returnTo += ":" + req.getServerPort();
        }

        returnTo += "/";

        return returnTo;
    }

    private void invalidateSession(HttpServletRequest req) {
        if( req.getSession() != null ) {
            req.getSession().invalidate();
        }
    }
}
