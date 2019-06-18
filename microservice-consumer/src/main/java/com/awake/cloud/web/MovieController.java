package com.awake.cloud.web;

import com.awake.cloud.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MovieController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/user/{id}")
    public UserEntity findById(@PathVariable Long id){
        UserEntity user = restTemplate.getForObject("http://localhost:8080/ms-provider/" + id, UserEntity.class);

        return user;
    }
}
