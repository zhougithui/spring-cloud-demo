package com.awake.cloud.service;

import com.awake.cloud.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Profile("springmvccontract")
@FeignClient(name = "ms-provider")
public interface UserFeignClient {

    @RequestMapping("/ms-provider/{id}")
    public UserEntity findById(@PathVariable("id") Long id);
}
