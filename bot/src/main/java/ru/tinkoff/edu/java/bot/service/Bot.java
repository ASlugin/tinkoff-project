package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.command.Command;

@Component
@Slf4j
public class Bot {
    private final TelegramBot tgBot;
    private final UserMessageProcessor processor;

    @Value("${bot.token}")
    private String token;

    public Bot(TelegramBot tgBot, UserMessageProcessor processor) {
        this.tgBot = tgBot;
        this.processor = processor;
    }

    @PostConstruct
    public void setListener() {
        log.warn(token);
        tgBot.execute(new SetMyCommands(processor.getCommands().stream()
                .map((Command x) -> x.getBotCommand()).toArray(BotCommand[]::new)));

        tgBot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                tgBot.execute(processor.process(update));
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
