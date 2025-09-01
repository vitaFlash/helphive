package com.hive.help.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "HelpHive API",
                version = "v1",
                description = "Role-based ticket management (Admin, Supervisor, Technician, User)"
        )
)
@Configuration
public class OpenApiConfig {
}
