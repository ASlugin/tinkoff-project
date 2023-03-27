package ru.tinkoff.edu.java.scrapper.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@OpenAPIDefinition(info = @Info(title = "Scrapper API",
        version = "1.0.1",
        description = "Some description of scrapper"))
public record ApplicationConfig(@NotNull String test) {}
