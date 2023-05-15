package ru.tinkoff.edu.java.bot.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tinkoff.edu.java.bot.dto.response.ApiErrorResponse;


@RestControllerAdvice
public class BotExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<ApiErrorResponse> handleException(IllegalArgumentException exception) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("Bot API error",
                HttpStatus.BAD_REQUEST.toString(),
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
