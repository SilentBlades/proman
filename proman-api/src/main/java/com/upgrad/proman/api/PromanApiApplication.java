package com.upgrad.proman.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.upgrad.proman.service.common.ServiceConfiguration;

@SpringBootApplication
@Import(ServiceConfiguration.class)
public class PromanApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PromanApiApplication.class, args);
    }
}