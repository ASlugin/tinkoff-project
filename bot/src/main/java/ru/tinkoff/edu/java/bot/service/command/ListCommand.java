package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.client.LinkResponse;
import ru.tinkoff.edu.java.bot.service.enums.ListOfCommands;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final ScrapperClient scrapperClient;

    public String command() {
        return ListOfCommands.LIST.getCommand();
    }

    public String description() {
        return ListOfCommands.LIST.getDescription();
    }

    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();

        LinkResponse[] links = scrapperClient.getAllLinks(chatId).links();
        if (links.length == 0) {
            return new SendMessage(chatId, "Отслеживаемых ссылок нету");
        }

        StringBuilder message = new StringBuilder();
        for (var link : links) {
            message.append(String.format("%d: %s\n", link.id(), link.url()));
        }
        return new SendMessage(chatId, message.toString());
    }
}
