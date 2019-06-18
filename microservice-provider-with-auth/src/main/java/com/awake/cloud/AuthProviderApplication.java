package com.awake.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class AuthProviderApplication {

    public static void main(String[] args){
        new SpringApplicationBuilder().profiles("provider8080").sources(AuthProviderApplication.class).run(args);
    }
}
