package dev.localcoder.springbooklibrary.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(
                new Info().title("Library Service Api")
                        .description("REST API for book and reader management")
                        .version("1.0.0"));
    }
}
