package ru.tinkoff.edu.java.scrapper.configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.configuration.access.AccessType;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@OpenAPIDefinition(info = @Info(title = "Scrapper API",
        version = "1.0.1",
        description = "Some description of scrapper"))
@EnableScheduling
public record ApplicationConfig(@NotNull String test,
                                Scheduler scheduler,
                                AccessType databaseAccessType,
                                RabbitMQ rabbitMQ,
                                boolean useQueue) {
    public record Scheduler(Duration interval) {}

    public record RabbitMQ(String exchange, String queue, String routingKey, String urlHost) {}
}
