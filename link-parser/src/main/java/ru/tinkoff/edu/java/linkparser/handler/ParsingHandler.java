package ru.tinkoff.edu.java.linkparser.handler;

import ru.tinkoff.edu.java.linkparser.result.ParsingResult;

public interface ParsingHandler {
    void setNextHandler(ParsingHandler nextHandler);

    ParsingHandler getNextHandler();

    ParsingResult handle(String link);
}
