package ru.tinkoff.edu.java.linkparser.handler;

import ru.tinkoff.edu.java.linkparser.result.ParsingResult;
import ru.tinkoff.edu.java.linkparser.result.StackOverflowParsingResult;

public final class StackOverflowParsingHandler extends AbstractParsingHandler {
    private static final String PATTERN = "^https://stackoverflow.com/questions/\\d+/[^/]+/?$";

    @Override
    protected Boolean doesLinkMatch(String link) {
        return link.matches(PATTERN);
    }

    @Override
    protected ParsingResult parse(String link) {
        String[] splitRes = link.split("/");
        return new StackOverflowParsingResult(splitRes[4]);
    }
}
