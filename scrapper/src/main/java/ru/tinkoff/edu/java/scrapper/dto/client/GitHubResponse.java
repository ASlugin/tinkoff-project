package ru.tinkoff.edu.java.scrapper.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubResponse(
        @JsonProperty("full_name") String fullNameOfRepository,
        @JsonProperty("updated_at") OffsetDateTime timeOfLastUpdate) {
}
