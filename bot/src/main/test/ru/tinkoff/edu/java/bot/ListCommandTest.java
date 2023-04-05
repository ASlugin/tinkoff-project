package ru.tinkoff.edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.client.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.client.ListLinksResponse;
import ru.tinkoff.edu.java.bot.service.command.ListCommand;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCommandTest {
    @Mock
    private ScrapperClient scrapperClient;

    @InjectMocks
    private ListCommand listCommand;

    private static Update updateMock;

    @BeforeAll
    static void initUpdateMock() {
        updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(123L);
    }

    @Test
    void messageShallBeSpecialIfListOfLinksIsEmptyTest() {
        // Given
        when(scrapperClient.getAllLinks(anyLong())).thenReturn(new ListLinksResponse(new LinkResponse[]{}, 0));

        // When
        SendMessage message = listCommand.handle(updateMock);

        // Then
        assertEquals("Отслеживаемых ссылок нету", message.getParameters().get("text"));
    }

    @Test
    void formatOfMessageIsCorrectTest() {
        // Given
        Map<Long, URI> mapIdUri = new LinkedHashMap<>();
        mapIdUri.put(32L, URI.create("https://google.com"));
        mapIdUri.put(33L, URI.create("https://yandex.ru"));

        List<LinkResponse> linkResponses = new ArrayList<>();
        StringBuilder expectedMessage = new StringBuilder();
        mapIdUri.forEach((id, uri) -> {
            linkResponses.add(new LinkResponse(id, uri));
            expectedMessage.append(String.format("%d: %s\n", id, uri));
        });

        when(scrapperClient.getAllLinks(anyLong()))
                .thenReturn(new ListLinksResponse(linkResponses.toArray(LinkResponse[]::new), linkResponses.size()));

        // When
        SendMessage message = listCommand.handle(updateMock);

        // Then
        assertEquals(expectedMessage.toString(), message.getParameters().get("text"));
    }
}
