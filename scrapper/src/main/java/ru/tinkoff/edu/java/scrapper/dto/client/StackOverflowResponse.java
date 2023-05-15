package ru.tinkoff.edu.java.scrapper.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackOverflowResponse(
        @JsonProperty("items") List<StackOverflowItem> item
) {
}
