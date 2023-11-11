package com.github.olson1998.synthwave.service.authorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = {
        "com.github.olson1998.synthwave.service",
        "com.github.olson1998.synthwave.support"
})
@EnableConfigurationProperties
@SpringBootApplication
public class Oauth2AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2AuthorizationServerApplication.class, args);
    }

}
