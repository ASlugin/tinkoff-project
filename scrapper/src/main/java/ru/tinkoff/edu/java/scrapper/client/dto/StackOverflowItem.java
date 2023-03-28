package ru.tinkoff.edu.java.scrapper.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record StackOverflowItem(
        @JsonProperty("question_id") long questionId,
        @JsonProperty("title") String title,
        @JsonProperty("is_answered") Boolean isAnswered,
        @JsonProperty("answer_count") OffsetDateTime answerCount,
        @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate,
        @JsonProperty("creation_date") OffsetDateTime creationDate,
        @JsonProperty("link") String link
) {
}
