package com.hive.help.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI helpHiveOpenAPI() {
                return new OpenAPI()
                        .info(new Info().title("HelpHive API").version("v1"))
                        .components(new Components().addSecuritySchemes(
                                "bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ))
                        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
        }
}
