package com.security.SpringSecurity.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/public")
public class PublicController {

    @PostMapping
    public String getPublicDetails(){
        return "public details accessed!";
    }

}
