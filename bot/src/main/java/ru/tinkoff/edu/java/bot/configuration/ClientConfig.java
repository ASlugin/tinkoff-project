package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;

@Component
public class ClientConfig {
    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperClient();
    }
}
