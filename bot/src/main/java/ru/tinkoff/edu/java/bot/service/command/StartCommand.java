package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.exception.ScrapperApiErrorException;
import ru.tinkoff.edu.java.bot.service.enums.ListOfCommands;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final ScrapperClient scrapperClient;

    public String command() {
        return ListOfCommands.START.getCommand();
    }

    public String description() {
        return ListOfCommands.START.getDescription();
    }

    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        String message;
        try {
            scrapperClient.registerChat(chatId);
            message = "Чат зарегистрирован!";
        } catch (ScrapperApiErrorException exc) {
            message = exc.getMessage();
        }
        return new SendMessage(chatId, message);
    }
}
