package com.awake.cloud.web;

import com.awake.cloud.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/user/{id}")
    public UserEntity findById(@PathVariable Long id){
        UserEntity user = restTemplate.getForObject("http://ms-provider/ms-provider/" + id, UserEntity.class);

        return user;
    }

    @GetMapping("/user-instance")
    public List<ServiceInstance> showInfo(){
        return discoveryClient.getInstances("ms-provider");
    }

    @GetMapping("/log-instance")
    public ServiceInstance logInfo(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("ms-provider");
        logger.info("{},{},{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getUri().toString());
        return serviceInstance;
    }
}
