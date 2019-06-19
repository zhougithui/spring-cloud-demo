package com.awake.cloud;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("feigncustom")
@Configuration
public class FeignConfiguration {

    @Bean
    public Contract feignContract(){
        return new Contract.Default();
    }
}
