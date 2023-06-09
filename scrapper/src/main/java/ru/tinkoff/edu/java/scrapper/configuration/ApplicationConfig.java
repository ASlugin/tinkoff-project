package ru.tinkoff.edu.java.scrapper.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.configuration.access.AccessType;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@OpenAPIDefinition(info = @Info(title = "Scrapper API",
        version = "1.0.1",
        description = "Some description of scrapper"))
@EnableScheduling
public record ApplicationConfig(@NotNull String test, Scheduler scheduler, AccessType databaseAccessType) {
    public record Scheduler(Duration interval) {}
}
