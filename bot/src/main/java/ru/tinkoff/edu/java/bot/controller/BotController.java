package ru.tinkoff.edu.java.bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.dto.response.ApiErrorResponse;

@RestController

public class BotController {
    @PostMapping(value = "/updates")
    @Operation(summary = "Отправить обновление", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Обновление обработано",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(description = "Некорректные параметры запроса",
                    responseCode = "400",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<?> updateLink(@RequestBody LinkUpdateRequest request) {
        if (request.id() < 1) {
            throw new IllegalArgumentException("id can't be negative or zero");
        }
        return ResponseEntity.ok().build();
    }
}
