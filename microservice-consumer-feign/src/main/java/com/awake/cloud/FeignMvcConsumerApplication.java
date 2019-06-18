package com.awake.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class FeignMvcConsumerApplication {

    public static void main(String[] args){
        new SpringApplicationBuilder().profiles("springmvccontract").sources(FeignDefaultConsumerApplication.class).run(args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
