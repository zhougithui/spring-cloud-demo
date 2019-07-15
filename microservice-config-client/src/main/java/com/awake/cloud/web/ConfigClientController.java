package com.awake.cloud.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientController {

    @Value("${username}")
    private String userName;
    @Value("${username2}")
    private String userName2;

    @GetMapping("/config")
    public String config(){
        return userName;
    }
}
