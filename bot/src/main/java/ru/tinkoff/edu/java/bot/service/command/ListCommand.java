package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.dto.client.LinkResponse;

@Component
public class ListCommand extends Command {
    private static final String COMMAND = "/list";
    private static final String DESCRIPTION = "Показать список отслеживаемых ссылок";

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
