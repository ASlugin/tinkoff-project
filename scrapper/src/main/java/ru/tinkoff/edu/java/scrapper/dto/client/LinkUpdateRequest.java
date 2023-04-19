package ru.tinkoff.edu.java.scrapper.dto.client;

import java.net.URI;

public record LinkUpdateRequest (
        long id,
        URI url,
        String description,
        long[] tgChatIds
) {}
