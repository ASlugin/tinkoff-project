package ru.tinkoff.edu.java.bot.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@OpenAPIDefinition(info = @Info(title = "Bot API",
        version = "1.0.1",
        description = "Some description of bot"))
public record ApplicationConfig(@NotNull String test, RabbitMQ rabbitMQ, boolean useQueue) {
    public record RabbitMQ(String exchange, String queue, String routingKey) {}
}