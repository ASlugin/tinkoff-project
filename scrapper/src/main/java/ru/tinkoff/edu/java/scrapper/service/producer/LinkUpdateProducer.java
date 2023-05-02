package ru.tinkoff.edu.java.scrapper.service.producer;

import ru.tinkoff.edu.java.scrapper.dto.client.LinkUpdateRequest;

public interface LinkUpdateProducer {
    void send(LinkUpdateRequest updateRequest);
}
