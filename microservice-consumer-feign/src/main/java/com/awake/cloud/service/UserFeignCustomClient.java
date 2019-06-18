package com.awake.cloud.service;

import com.awake.cloud.FeignConfiguration;
import com.awake.cloud.entity.UserEntity;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@Profile("feigncustom")
@FeignClient(name = "ms-provider", configuration = FeignConfiguration.class)
public interface UserFeignCustomClient {

    @RequestLine("GET /ms-provider/{id}")
    public UserEntity findById(@Param("id") Long id);
}
