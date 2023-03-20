package ru.tinkoff.edu.java.linkparser.handler;

import ru.tinkoff.edu.java.linkparser.result.GithubParsingResult;
import ru.tinkoff.edu.java.linkparser.result.ParsingResult;

public final class GithubParsingHandler extends AbstractParsingHandler {

    private static final String PATTERN = "^https://github.com/[a-zA-Z0-9-]{1,39}/[a-zA-Z0-9-_.]+/?$";

    @Override
    public ParsingResult handle(String link) {
        if (!link.matches(PATTERN)) {
            if (getNextHandler() != null) {
                return getNextHandler().handle(link);
            }
            return null;
        }

        String[] splitRes = link.split("/");
        return new GithubParsingResult(splitRes[3], splitRes[4]);
    }
}
