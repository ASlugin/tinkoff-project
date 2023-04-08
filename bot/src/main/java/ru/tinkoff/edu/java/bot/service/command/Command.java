package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    String command();

    String description();

    default SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        return new SendMessage(String.valueOf(chatId), description());
    }

    default boolean supports(Update update) {
        return update.message().text().equals(command());
    }

    default BotCommand getBotCommand() {
        return new BotCommand(command(), description());
    }
}
