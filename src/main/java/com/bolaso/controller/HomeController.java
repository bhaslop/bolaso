package com.bolaso.controller;

import com.bolaso.security.TokenAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(Model model, final Authentication authentication) {

        if( authentication instanceof TokenAuthentication ) {
            TokenAuthentication tokenAuthentication = (TokenAuthentication)authentication;
            model.addAttribute("profile", tokenAuthentication.getClaims());
        }

        return "index";
    }
}
