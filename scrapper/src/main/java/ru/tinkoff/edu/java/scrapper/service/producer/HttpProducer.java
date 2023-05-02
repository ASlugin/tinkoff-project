package ru.tinkoff.edu.java.scrapper.service.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.dto.client.LinkUpdateRequest;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class HttpProducer implements LinkUpdateProducer {
    private final BotClient botClient;

    @Override
    public void send(LinkUpdateRequest updateRequest) {
        botClient.updates(updateRequest);
    }
}
