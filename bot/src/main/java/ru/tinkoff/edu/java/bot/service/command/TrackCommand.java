package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.dto.client.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.client.LinkResponse;

import java.net.URI;

@Component
public class TrackCommand extends Command {
    private static final String COMMAND = "/track";
    private static final String DESCRIPTION = "Начать отслеживание ссылки";

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

        AddLinkRequest request = new AddLinkRequest(URI.create("https://addlink.com"));
        LinkResponse response = scrapperClient.trackLink(chatId, request);

        StringBuilder message = new StringBuilder();
        if (response != null) {
            message.append(String.format("Ссылка %s добавлена для отслеживания", response.url().toString()));
        }

        return new SendMessage(chatId, message.toString());
    }
}
