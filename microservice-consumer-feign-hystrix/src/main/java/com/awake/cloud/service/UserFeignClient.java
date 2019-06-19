package com.awake.cloud.service;

import com.awake.cloud.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "ms-provider", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

    @RequestMapping("/ms-provider/{id}")
    public UserEntity findById(@PathVariable("id") Long id);
}
