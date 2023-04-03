package ru.tinkoff.edu.java.bot.service.command;

import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private static final String COMMAND = "/list";
    private static final String DESCRIPTION = "Показать список отслеживаемых ссылок";

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }
}
