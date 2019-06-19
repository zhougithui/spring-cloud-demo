package com.awake.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ConfigClientApplication {

    public static void main(String[] args){
        new SpringApplicationBuilder().profiles("dev").sources(ConfigClientApplication.class).run(args);
//        SpringApplication.run(ConfigClientApplication.class, args);
    }

}
