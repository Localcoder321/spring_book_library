package dev.localcoder.springbooklibrary.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    final String securityScheme = "bearerAuth";

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(
                new Info().title("Library Service Api")
                        .description("REST API for book and reader management")
                        .version("1.0.0")).addSecurityItem(new SecurityRequirement()
                .addList(securityScheme))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(securityScheme,
                new SecurityScheme()
                .name(securityScheme)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Enter JWT token with Bearer prefix")));
    }
}
