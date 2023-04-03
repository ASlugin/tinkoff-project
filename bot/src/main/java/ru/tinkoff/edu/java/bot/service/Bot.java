package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Bot {
    private final TelegramBot tgBot;
    private final UserMessageProcessor processor;

    public Bot(TelegramBot tgBot, UserMessageProcessor processor) {
        this.tgBot = tgBot;
        this.processor = processor;
    }

    @PostConstruct
    public void setListener() {
        tgBot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {
                for (var update : updates){
                    System.out.println(update.message().text());
                    tgBot.execute(processor.process(update));
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }
}
