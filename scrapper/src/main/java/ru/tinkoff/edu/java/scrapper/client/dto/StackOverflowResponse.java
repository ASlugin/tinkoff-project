package ru.tinkoff.edu.java.scrapper.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackOverflowResponse(
        @JsonProperty("items") List<StackOverflowItem> item
) {
}
