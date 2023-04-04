package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;

public abstract class Command {
    @Autowired
    protected ScrapperClient scrapperClient;

    protected abstract String command();

    protected abstract String description();

    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        return new SendMessage(String.valueOf(chatId), description());
    }

    public boolean supports(Update update) {
        return update.message().text().equals(command());
    }

    public BotCommand getBotCommand() {
        return new BotCommand(command(), description());
    }
}
