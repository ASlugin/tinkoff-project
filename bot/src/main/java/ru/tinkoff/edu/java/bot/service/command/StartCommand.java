package ru.tinkoff.edu.java.bot.service.command;

import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    private static final String COMMAND = "/start";
    private static final String DESCRIPTION = "Зарегистрировать пользователя";

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }
}
