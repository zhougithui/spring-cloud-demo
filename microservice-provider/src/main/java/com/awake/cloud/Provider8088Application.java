package com.awake.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class Provider8088Application {

    public static void main(String[] args){
        new SpringApplicationBuilder().profiles("provider8088").sources(Provider8088Application.class).run(args);
    }
}
