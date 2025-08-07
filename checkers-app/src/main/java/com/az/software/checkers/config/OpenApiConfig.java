package com.az.software.checkers.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    /**
     * Customize basic API info
     */
    @Bean
    public OpenAPI checkersOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Checkers Game API")
                        .description("Endpoints for playing a game of Checkers")
                        .version("1.0"));
    }

    /**
     * Group /api/** endpoints under “checkers‐api”
     */
    @Bean
    public GroupedOpenApi checkersApi() {
        return GroupedOpenApi.builder()
                .group("checkers-api")
                .pathsToMatch("/api/**")
                .build();
    }
}
