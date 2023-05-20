package com.kodlamaio.filterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.kodlamaio.commonpackage.configuration", "com.kodlamaio.filterservice"})
public class FilterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilterServiceApplication.class, args);
    }

}
