package ru.tinkoff.edu.java.scrapper.exception;

public class TgChatNotFoundException extends Exception {
    public TgChatNotFoundException() {
        super();
    }

    public TgChatNotFoundException(String message) {
        super(message);
    }
}
