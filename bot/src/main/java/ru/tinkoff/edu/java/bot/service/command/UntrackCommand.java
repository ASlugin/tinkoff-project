package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.dto.client.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.client.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.client.RemoveLinkRequest;

import java.net.URI;

@Component
public class UntrackCommand extends Command {
    private static final String COMMAND = "/untrack";
    private static final String DESCRIPTION = "Прекратить отслеживание ссылки";

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

        RemoveLinkRequest request = new RemoveLinkRequest(URI.create("https://deletelink.com"));
        LinkResponse response = scrapperClient.untrackLink(chatId, request);

        StringBuilder message = new StringBuilder();
        if (response != null) {
            message.append(String.format("Ссылка %s больше не отслеживается", response.url().toString()));
        }

        return new SendMessage(chatId, message.toString());
    }
}
