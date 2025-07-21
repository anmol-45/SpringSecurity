package com.security.SpringSecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/secured")
public class SecuredController {

    @PreAuthorize("hasRole(ADMIN)")
    @PostMapping
    public String getSecureDetails(){
        return "secured details accessed!";
    }
}
