package ru.tinkoff.edu.java.bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.service.UserMessageProcessor;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BotController {
    private final UserMessageProcessor messageProcessor;

    @PostMapping(value = "/updates")
    @Operation(summary = "Отправить обновление")
    @ApiResponse(responseCode = "200", description = "Обновление обработано",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    public ResponseEntity<?> updateLink(@RequestBody LinkUpdateRequest request) {
        if (request.id() < 1) {
            throw new IllegalArgumentException("id can't be negative or zero");
        }
        log.info("Update link by http");
        messageProcessor.sendUpdateMessages(request);
        return ResponseEntity.ok().build();
    }
}
