package ru.tinkoff.edu.java.bot.exception;

public class ScrapperApiErrorException extends RuntimeException {
    public ScrapperApiErrorException(String message) {
        super(message);
    }
}
