package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.client.LinkUpdateRequest;

public class BotClient {
    private static final String DEFAULT_BASE_URL = "http://localhost:8081";
    private final WebClient webClient;

    public BotClient() {
        this(DEFAULT_BASE_URL);
    }

    public BotClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl == null || baseUrl.isEmpty() ? DEFAULT_BASE_URL : baseUrl);
    }

    public void updates(LinkUpdateRequest request) {
        this.webClient
                .post()
                .uri("/updates")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
