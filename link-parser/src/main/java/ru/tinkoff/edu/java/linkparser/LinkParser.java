package ru.tinkoff.edu.java.linkparser;

import java.util.Iterator;
import java.util.List;
import ru.tinkoff.edu.java.linkparser.handler.GitHubParsingHandler;
import ru.tinkoff.edu.java.linkparser.handler.ParsingHandler;
import ru.tinkoff.edu.java.linkparser.handler.StackOverflowParsingHandler;
import ru.tinkoff.edu.java.linkparser.result.ParsingResult;

public class LinkParser {
    private static ParsingHandler handler;

    public static ParsingResult parse(String url) {
        if (handler == null) {
            buildHandler();
        }
        return handler.handle(url);
    }

    private static void buildHandler() {
        List<ParsingHandler> listOfHandlers = List.of(
                new GitHubParsingHandler(),
                new StackOverflowParsingHandler());

        Iterator<ParsingHandler> listOfHandlersIterator = listOfHandlers.listIterator();

        var localHandler = listOfHandlersIterator.next();
        while (listOfHandlersIterator.hasNext()) {
            localHandler.setNextHandler(listOfHandlersIterator.next());
            localHandler = localHandler.getNextHandler();
        }
        handler = listOfHandlers.get(0);
    }
}
