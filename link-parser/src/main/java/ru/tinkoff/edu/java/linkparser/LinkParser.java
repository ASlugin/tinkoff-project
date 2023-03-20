package ru.tinkoff.edu.java.linkparser;

import ru.tinkoff.edu.java.linkparser.handler.GithubParsingHandler;
import ru.tinkoff.edu.java.linkparser.handler.ParsingHandler;
import ru.tinkoff.edu.java.linkparser.handler.StackOverflowParsingHandler;
import ru.tinkoff.edu.java.linkparser.result.ParsingResult;

import java.util.Iterator;
import java.util.List;

public class LinkParser {
    public static ParsingResult parse(String url) {
        List<ParsingHandler> listOfHandlers = List.of(
                new GithubParsingHandler(),
                new StackOverflowParsingHandler());

        Iterator<ParsingHandler> listOfHandlersIterator = listOfHandlers.listIterator();

        var localHandler = listOfHandlersIterator.next();
        while (listOfHandlersIterator.hasNext()) {
            localHandler.setNextHandler(listOfHandlersIterator.next());
            localHandler = localHandler.getNextHandler();
        }

        var handler = listOfHandlers.get(0);
        return handler.handle(url);
    }
}