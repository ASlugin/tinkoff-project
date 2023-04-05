package ru.tinkoff.edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.edu.java.bot.service.UserMessageProcessor;
import ru.tinkoff.edu.java.bot.service.command.ListCommand;
import ru.tinkoff.edu.java.bot.service.command.StartCommand;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageProcessorTest {

    @Test
    void processorShallReturnSpecialMessageIfCommandIsUnknown() {
        // Given
        UserMessageProcessor processor = new UserMessageProcessor(List.of(
                new ListCommand(),
                new StartCommand()
        ));

        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.chat()).thenReturn(chatMock);
        when(messageMock.text()).thenReturn("/unknownCommand");
        when(chatMock.id()).thenReturn(123L);

        // When
        SendMessage message = processor.process(updateMock);

        // Then
        assertEquals("Неизвестная команда\n/help - список с командами", message.getParameters().get("text"));
    }
}
