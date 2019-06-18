package com.awake.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class Provider8080Application {

    public static void main(String[] args){
        new SpringApplicationBuilder().profiles("provider8080").sources(Provider8080Application.class).run(args);
    }
}
