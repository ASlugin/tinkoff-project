package ru.tinkoff.edu.java.bot.service.command;

import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {
    private static final String COMMAND = "/untrack";
    private static final String DESCRIPTION = "Прекратить отслеживание ссылки";

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }
}
