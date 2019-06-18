package com.awake.cloud.web;

import com.awake.cloud.entity.UserEntity;
import com.awake.cloud.service.UserFeignCustomClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Profile("feigncustom")
@RestController
public class FeignMovieController {

    @Autowired
    private UserFeignCustomClient userFeignCustomClient;

    @GetMapping("/user/{id}")
    public UserEntity findById(@PathVariable Long id){
        return userFeignCustomClient.findById(id);
    }

}
