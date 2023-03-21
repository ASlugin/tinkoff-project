package ru.tinkoff.edu.java.linkparser.handler;

import ru.tinkoff.edu.java.linkparser.result.ParsingResult;

public abstract class AbstractParsingHandler implements ParsingHandler{
    private ParsingHandler nextHandler;

    @Override
    public void setNextHandler(ParsingHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public ParsingHandler getNextHandler() {
        return nextHandler;
    }

    @Override
    public ParsingResult handle(String link) {
        if (!doesLinkMatch(link)) {
            return getNextHandler() != null ? getNextHandler().handle(link) : null;
        }
        return parse(link);
    }

    protected abstract Boolean doesLinkMatch(String link);

    protected abstract ParsingResult parse(String link);

}
