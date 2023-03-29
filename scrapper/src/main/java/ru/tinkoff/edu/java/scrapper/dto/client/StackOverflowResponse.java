package ru.tinkoff.edu.java.scrapper.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.tinkoff.edu.java.scrapper.dto.client.StackOverflowItem;

import java.util.List;

public record StackOverflowResponse(
        @JsonProperty("items") List<StackOverflowItem> item
) {
}
