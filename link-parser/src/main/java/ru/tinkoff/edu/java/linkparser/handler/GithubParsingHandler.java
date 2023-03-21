package ru.tinkoff.edu.java.linkparser.handler;

import ru.tinkoff.edu.java.linkparser.result.GithubParsingResult;
import ru.tinkoff.edu.java.linkparser.result.ParsingResult;

public final class GithubParsingHandler extends AbstractParsingHandler {
    private static final String PATTERN = "^https://github.com/[a-zA-Z0-9-]+/[a-zA-Z0-9-_.]+/?$";

    @Override
    protected Boolean doesLinkMatch(String link) {
        return link.matches(PATTERN);
    }

    @Override
    protected ParsingResult parse(String link) {
        String[] splitRes = link.split("/");
        return new GithubParsingResult(splitRes[3], splitRes[4]);
    }
}
