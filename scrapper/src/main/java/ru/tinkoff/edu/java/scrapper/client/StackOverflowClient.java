package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.client.StackOverflowResponse;

public class StackOverflowClient {
    private final WebClient webClient;
    private static final String DEFAULT_BASE_URL = "https://api.stackexchange.com/2.3";

    public StackOverflowClient() {
        this(DEFAULT_BASE_URL);
    }

    public StackOverflowClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl == null || baseUrl.isEmpty() ? DEFAULT_BASE_URL : baseUrl);
    }

    public Mono<StackOverflowResponse> fetchQuestion(String id) {
        return this.webClient
                .get()
                .uri("/questions/" + id + "?order=desc&sort=activity&site=stackoverflow")
                .retrieve()
                .bodyToMono(StackOverflowResponse.class);
    }
}
