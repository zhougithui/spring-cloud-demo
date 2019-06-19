package com.awake.cloud.service;

import com.awake.cloud.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserFeignClientFallback implements UserFeignClient {

    @Override
    public UserEntity findById(Long id) {
        UserEntity user = new UserEntity();
        user.setAge(10);
        user.setBalance(BigDecimal.ONE);
        user.setName("hystrix-feign");
        user.setUsername("hystrix-feign");
        user.setId(-1L);
        return user;
    }
}
