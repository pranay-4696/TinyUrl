package com.tinyurl.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class UrlConfig {

    @Value("${short.url.length}")
    private int shortUrlLength;

}
