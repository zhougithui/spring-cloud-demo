package com.awake.cloud.web;

import com.awake.cloud.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Profile("springmvccontract")
@RestController
public class MovieController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/user/{id}")
    public UserEntity findById(@PathVariable Long id){
        UserEntity user = restTemplate.getForObject("http://localhost:8080/ms-provider/" + id, UserEntity.class);

        return user;
    }

    @GetMapping("/user-instance")
    public List<ServiceInstance> showInfo(){
        return discoveryClient.getInstances("ms-provider");
    }
}
