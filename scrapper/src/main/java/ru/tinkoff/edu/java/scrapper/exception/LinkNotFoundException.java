package ru.tinkoff.edu.java.scrapper.exception;

public class LinkNotFoundException extends Exception {
    public LinkNotFoundException() {
        super();
    }

    public LinkNotFoundException(String message) {
        super(message);
    }
}
