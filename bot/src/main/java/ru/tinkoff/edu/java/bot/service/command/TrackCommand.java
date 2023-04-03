package ru.tinkoff.edu.java.bot.service.command;

import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    private static final String COMMAND = "/track";
    private static final String DESCRIPTION = "Начать отслеживание ссылки";

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }
}
