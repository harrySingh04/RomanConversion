package org.romanconversion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${client.url}")
    private String clientUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/romannumeral*") // Match all urls under romannumeral
                .allowedOrigins(clientUrl)
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowedHeaders("Access-Control-Allow-Origin")
                .allowCredentials(false);
    }

}
