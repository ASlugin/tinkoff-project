package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.UserMessageProcessor;

@Component
@RequiredArgsConstructor
public class HelpCommand extends Command {
    private static final String COMMAND = "/help";
    private static final String DESCRIPTION = "Вывести окно с командами";
    private final ApplicationContext context;

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
        StringBuilder message = new StringBuilder();
        for (var command : context.getBean(UserMessageProcessor.class).getCommands()){
            message.append(String.format("%s — %s\n", command.command(), command.description()));
        }
        return new SendMessage(String.valueOf(chatId), message.toString());
    }
}
