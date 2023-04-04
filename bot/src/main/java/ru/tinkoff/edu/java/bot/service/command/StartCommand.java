package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand extends Command {
    private static final String COMMAND = "/start";
    private static final String DESCRIPTION = "Зарегистрировать пользователя";

    @Override
    protected String command() {
        return COMMAND;
    }

    @Override
    protected String description() {
        return DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        scrapperClient.registerChat(chatId);
        return new SendMessage(chatId, "Чат зарегистрирован");
    }
}
