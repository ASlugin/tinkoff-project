package ru.tinkoff.edu.java.bot.dto.response;

import java.util.List;

public record ApiErrorResponse(
        String description,
        String code,
        String exceptionName,
        String exceptionMessage,
        String[] stacktrace
) {}
