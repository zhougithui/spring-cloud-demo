package com.zcloud.commons;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * eureka客户端
 * 需要配置eureka的注册中心地址以及client应用的名称
 * 如果应用名称一样则被视为集群的节点
 */
@EnableEurekaClient
@SpringBootApplication
public class CommonsApplication {

    public static void main(String[] args){
        new SpringApplicationBuilder(CommonsApplication.class).build().run(args);
    }
}
