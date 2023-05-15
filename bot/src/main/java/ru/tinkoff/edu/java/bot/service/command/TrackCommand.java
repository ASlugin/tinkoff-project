package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.client.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.client.LinkResponse;
import ru.tinkoff.edu.java.bot.service.enums.ListOfCommands;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command {
    private final ScrapperClient scrapperClient;

    public String command() {
        return ListOfCommands.TRACK.getCommand();
    }

    public String description() {
        return ListOfCommands.TRACK.getDescription();
    }

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
