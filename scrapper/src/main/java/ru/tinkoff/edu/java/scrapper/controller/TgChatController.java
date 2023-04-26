package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exception.IncorrectParametersOfRequestException;
import ru.tinkoff.edu.java.scrapper.exception.TgChatNotFoundException;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RestController
@RequestMapping("/tg-chat")
@Slf4j
public class TgChatController {
    private final TgChatService chatService;

    public TgChatController(@Qualifier("jpaChatService") TgChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(value = "/{id}" )
    @Operation(summary = "Зарегистрировать чат", responses = {
            @ApiResponse(responseCode = "200", description = "Чат зарегистрирован",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<?> registerChat(@PathVariable long id) throws IncorrectParametersOfRequestException {
        log.info("Register chat " + id);
        if (id < 1) {
            throw new IncorrectParametersOfRequestException("id can't be negative or zero");
        }
        if (!chatService.register(id)) {
            throw new IncorrectParametersOfRequestException("Чат уже зарегистрирован!");
        };
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Удалить чат", responses = {
            @ApiResponse(responseCode = "200", description = "Чат успешно удалён",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Чат не существует",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<?> deleteChat(@PathVariable long id) throws IncorrectParametersOfRequestException, TgChatNotFoundException {
        log.info("Delete chat " + id);
        if (id < 1) {
            throw new IncorrectParametersOfRequestException("id can't be negative or zero");
        }
        if (!chatService.isChatExist(id)) {
            throw new TgChatNotFoundException("Chat with given id does not exist");
        }
        chatService.unregister(id);
        return ResponseEntity.ok().build();
    }
}
