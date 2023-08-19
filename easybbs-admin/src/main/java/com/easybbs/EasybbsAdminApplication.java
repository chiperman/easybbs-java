package com.easybbs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EasybbsAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasybbsAdminApplication.class, args);
    }
}
