package ru.tinkoff.edu.java.bot.client;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.bot.dto.client.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.client.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.client.ListLinksResponse;
import ru.tinkoff.edu.java.bot.dto.client.RemoveLinkRequest;

public class ScrapperClient {
    private static final String DEFAULT_BASE_URL = "http://localhost:8080";
    private final WebClient client;

    public ScrapperClient() {
        this(DEFAULT_BASE_URL);
    }

    public ScrapperClient(String baseUrl) {
        this.client = WebClient.builder().baseUrl(baseUrl == null || baseUrl.isEmpty() ? DEFAULT_BASE_URL : baseUrl).build();
    }

    public ListLinksResponse getAllLinks(long chatId) {
        return client
                .get()
                .uri("/links")
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .retrieve()
                .bodyToMono(ListLinksResponse.class)
                .block();
    }

    public LinkResponse trackLink(long chatId, AddLinkRequest request) {
        return client
                .post()
                .uri("/links")
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

    public LinkResponse untrackLink(long chatId, RemoveLinkRequest request) {
        return client
                .method(HttpMethod.DELETE)
                .uri("/links")
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

    public void registerChat(long chatId) {
        client.post()
                .uri("/tg-chat/" + String.valueOf(chatId))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteChat(long chatId) {
        client.delete()
                .uri("/tg-chat/" + String.valueOf(chatId))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
