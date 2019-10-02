package com.bolaso.controller;

import com.bolaso.security.TokenAuthentication;
import com.bolaso.security.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/profile")
    protected String profile(final Model model, final Authentication authentication) {
         TokenAuthentication tokenAuthentication = (TokenAuthentication)authentication;

         if( tokenAuthentication == null ) {
             return "redirect:/login";
         }

         String profileJson = TokenUtils.claimsAsJson(tokenAuthentication.getClaims());

         model.addAttribute("profile", tokenAuthentication.getClaims());
         model.addAttribute("profileJson", profileJson);

         return "profile";
    }
}
