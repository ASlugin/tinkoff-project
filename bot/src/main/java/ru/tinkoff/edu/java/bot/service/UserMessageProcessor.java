package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.command.Command;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMessageProcessor {
    private final List<? extends Command> commands;

    public List<? extends Command> getCommands() {
        return commands;
    }

    public SendMessage process(Update update) {
        try {
            for (var command : commands) {
                if (command.supports(update)) {
                    return command.handle(update);
                }
            }
            return new SendMessage(String.valueOf(update.message().chat().id()), "Неизвестная команда\n/help - список с командами");
        }
        catch (NullPointerException exc) {
            return new SendMessage(String.valueOf(update.message().chat().id()), "Что-то пошло не так\n/help");
        }
    }
}