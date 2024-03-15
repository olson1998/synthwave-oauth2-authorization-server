package com.github.olson1998.synthwave.service.authorizationserver;

import com.github.olson1998.synthwave.support.masteritem.annotation.EnableMITransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@ComponentScan(basePackages = {
        "com.github.olson1998.synthwave.service",
        "com.github.olson1998.synthwave.support"
})
@EnableWebSecurity
@EnableMITransaction
@EnableConfigurationProperties

@SpringBootApplication
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

}
