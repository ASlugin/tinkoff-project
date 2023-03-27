package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.scrapper.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exception.IncorrectParametersOfRequestException;
import ru.tinkoff.edu.java.scrapper.exception.LinkNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.TgChatNotFoundException;

import java.util.Arrays;

@RestControllerAdvice
public class ScrapperExceptionHandler {
    @ExceptionHandler({IncorrectParametersOfRequestException.class})
    public ResponseEntity<ApiErrorResponse> handleIncorrectParametersException(IncorrectParametersOfRequestException exception) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("Request to scrapper error",
                "400",
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TgChatNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> handleTgChatNotFoundException(TgChatNotFoundException exception) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("Telegram chat not found",
                "404",
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({LinkNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> handleLinkNotFoundException(LinkNotFoundException exception) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("Link not found",
                "404",
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
