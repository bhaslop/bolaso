package com.bolaso.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @RequestMapping("/api/user")
    public String get() {
        return "Hey! I work!";
    }
}
