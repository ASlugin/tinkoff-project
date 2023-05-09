package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.client.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.client.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.service.enums.ListOfCommands;


@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command {
    private final ScrapperClient scrapperClient;

    public String command() {
        return ListOfCommands.UNTRACK.getCommand();
    }

    public String description() {
        return ListOfCommands.UNTRACK.getDescription();
    }

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
