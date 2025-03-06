package com.example.school.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// This class To prevent CORS Error 
@Configuration
public class WebConfig {
    
    @Bean
    public WebMvcConfigurer crosConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .allowCredentials(true)
                .allowedHeaders("*");
            }
        };
    }
}
