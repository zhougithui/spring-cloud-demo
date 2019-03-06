package com.zcloud.commons;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CommonsApplication {

    public static void main(String[] args){
        new SpringApplicationBuilder(CommonsApplication.class).build().run(args);
    }
}
