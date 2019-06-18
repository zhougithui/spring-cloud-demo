package com.zcloud.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Profile;

/**
 * 服务注册
 * Spring Cloud使用erureka server,  然后所有需要访问配置文件的应用都作为一个erureka client注册上去。
 * eureka是一个高可用的组件，它没有后端缓存，每一个实例注册之后需要向注册中心发送心跳，
 * 在默认情况下erureka server也是一个eureka client ,必须要指定一个 server。
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaHA2ServerApplication {

    /**
     * 启动后访问http://localhost:8761可以看到server的状态以及目前已经注册的client
     * @param args
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder().profiles("ha-ds2").sources(EurekaHA2ServerApplication.class).run(args);
    }
}