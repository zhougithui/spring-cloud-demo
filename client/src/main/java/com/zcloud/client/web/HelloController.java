package com.zcloud.client.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/hello")
    public String hello(String name){
        //设置Http Header
        HttpHeaders headers = new HttpHeaders();
        //设置请求媒体数据类型
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //设置返回媒体数据类型
        HttpEntity<String> httpEntity = new HttpEntity<>("name=" + name, headers);
        return restTemplate.postForObject("http://SERVICE-HELLOWORLD/commons/hello", httpEntity, String.class);
    }
}
