package com.awake.cloud.web;

import com.awake.cloud.dao.UserRepository;
import com.awake.cloud.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public UserEntity findById(@PathVariable Long id){
        UserEntity user = userRepository.getOne(id);
        return user;
    }

}
