package com.awake.cloud.web;

import com.awake.cloud.entity.UserEntity;
import com.awake.cloud.service.UserFeignClient;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Import(FeignClientsConfiguration.class)
@RestController
public class MovieController {
    private UserFeignClient userFeignClient;

    private UserFeignClient adminFeignClient;

    @Autowired
    public MovieController(Decoder decoder, Encoder encoder, Client client, Contract contract){
        this.userFeignClient = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract)
                .requestInterceptor(new BasicAuthRequestInterceptor("user", "password1"))
                .target(UserFeignClient.class, "http://ms-provider/ms-provider/");
        this.adminFeignClient = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract)
                .requestInterceptor(new BasicAuthRequestInterceptor("admin", "password2"))
                .target(UserFeignClient.class, "http://ms-provider/ms-provider/");
    }


    @GetMapping("/user/{id}")
    public UserEntity findByIdUser(@PathVariable Long id){
        UserEntity user = userFeignClient.findById(id);

        return user;
    }
    @GetMapping("/admin/{id}")
    public UserEntity findByIdAdmin(@PathVariable Long id){
        UserEntity user = adminFeignClient.findById(id);

        return user;
    }

}
