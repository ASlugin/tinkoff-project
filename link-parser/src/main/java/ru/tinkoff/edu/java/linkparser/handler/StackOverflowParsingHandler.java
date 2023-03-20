package ru.tinkoff.edu.java.linkparser.handler;

import ru.tinkoff.edu.java.linkparser.result.GithubParsingResult;
import ru.tinkoff.edu.java.linkparser.result.ParsingResult;
import ru.tinkoff.edu.java.linkparser.result.StackOverflowParsingResult;

public final class StackOverflowParsingHandler extends AbstractParsingHandler {

    private static final String PATTERN = "^https://stackoverflow.com/questions/\\d+/[^/]+/?$";
    @Override
    public ParsingResult handle(String link) {
        if (!link.matches(PATTERN)) {
            if (getNextHandler() != null) {
                return getNextHandler().handle(link);
            }
            return null;
        }

        String[] splitRes = link.split("/");
        return new StackOverflowParsingResult(splitRes[4]);
    }
}
