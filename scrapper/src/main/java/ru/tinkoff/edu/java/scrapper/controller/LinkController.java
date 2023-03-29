package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.exception.IncorrectParametersOfRequestException;
import ru.tinkoff.edu.java.scrapper.exception.LinkNotFoundException;


@RestController
public class LinkController {
    @GetMapping(value = "/links")
    @Operation(summary = "Получить все отслеживаемые ссылки", responses = {
            @ApiResponse(responseCode = "200", description = "Ссылки успешно получены",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ListLinksResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<?> getAllLinks(@RequestHeader("Tg-Chat-Id") long chatId) throws IncorrectParametersOfRequestException {
        if (chatId < 1) {
            throw new IncorrectParametersOfRequestException("Chat id can't be negative or zero");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/links")
    @Operation(summary = "Добавить отслеживание ссылки", responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LinkResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<?> addLink(@RequestHeader("Tg-Chat-Id") long chatId, @RequestBody AddLinkRequest request)
            throws IncorrectParametersOfRequestException {
        if (chatId < 1) {
            throw new IncorrectParametersOfRequestException("Chat id can't be negative or zero");
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/links")
    @PostMapping(value = "/links")
    @Operation(summary = "Убрать отслеживание ссылки", responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LinkResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ссылка не найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<?> deleteLink(@RequestHeader("Tg-Chat-Id") long chatId, @RequestBody RemoveLinkRequest request)
            throws IncorrectParametersOfRequestException, LinkNotFoundException {
        if (chatId < 1) {
            throw new IncorrectParametersOfRequestException("Chat id can't be negative or zero");
        }

        // if link not found in list of existing links
        if (request.link().toString().equals("ololo")) {
            throw new LinkNotFoundException("Link not found in list of existing links");
        }

        return ResponseEntity.ok().build();
    }
}
