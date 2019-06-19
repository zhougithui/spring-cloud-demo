package com.awake.cloud.service;

import com.awake.cloud.entity.UserEntity;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {
    private static final Logger logger = LoggerFactory.getLogger(UserFeignClientFallbackFactory.class);

    @Override
    public UserFeignClient create(Throwable cause) {
        logger.error("用户服务异常", cause);
        return id -> {
                UserEntity user = new UserEntity();
                user.setAge(10);
                user.setBalance(BigDecimal.ONE);
                user.setName("hystrix-feign-factory");
                user.setUsername("hystrix-feign-factory");
                user.setId(-1L);
                return user;
            };
    }
}
