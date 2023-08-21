package com.easybbs.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class WebConfig extends AppConfig {
    @Value("${spring.mail.username:}")
    private String sendUserName;
}
