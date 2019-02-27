package com.zcloud.client.feign.service;

import org.springframework.stereotype.Component;

@Component
public class FallbackHelloService implements HelloWorldService {

    @Override
    public String sayHello() {
        return "hello world service is not available ! feign";
    }
}
