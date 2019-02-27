package com.zcloud.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用config的客户端需要制定server url地址
 * git分支
 * profile环境（可选）
 *
 */
@SpringBootApplication
@RestController
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

    /**
     * 向config server发送获取信息指令
     * config server从git中拉取配置内容，将结果返回给客户端
     */
    @Value("${hello}")
    String hello;

    /**
     * 访问http://locahost/8881/hello即可拿到git配置文件的内容
     * @return
     */
    @RequestMapping(value = "/hello")
    public String hello(){
        return hello;
    }
}