package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.enums.ListOfCommands;

@Component
public class HelpCommand implements Command {
    public String command() {
        return ListOfCommands.HELP.getCommand();
    }

    public String description() {
        return ListOfCommands.HELP.getDescription();
    }

    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();

        StringBuilder message = new StringBuilder();
        for (var command : ListOfCommands.values()) {
            message.append(command.getCommandWithDescription()).append("\n");
        }

        return new SendMessage(String.valueOf(chatId), message.toString());
    }
}
