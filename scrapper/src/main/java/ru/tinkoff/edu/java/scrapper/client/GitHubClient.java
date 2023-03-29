package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.client.GitHubResponse;

public class GitHubClient {
    private static final String DEFAULT_BASE_URL = "https://api.github.com";
    private static final String GITHUB_API_VERSION = "2022-11-28";
    private final WebClient webClient;

    public GitHubClient() {
        this(DEFAULT_BASE_URL);
    }

    public GitHubClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl == null || baseUrl.isEmpty() ? DEFAULT_BASE_URL : baseUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add("Accept", "application/vnd.github+json");
                    httpHeaders.add("X-GitHub-Api-Version", GITHUB_API_VERSION);
                }).build();
    }

    public Mono<GitHubResponse> fetchRepository(String user, String repository) {
        return this.webClient
                .get()
                .uri("/repos/" + user + "/" + repository)
                .retrieve()
                .bodyToMono(GitHubResponse.class);
    }
}
