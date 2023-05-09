package ru.tinkoff.edu.java.scrapper.persistence.repository;

import java.util.List;
import ru.tinkoff.edu.java.scrapper.persistence.model.Chat;

public interface ChatRepository {
    Chat addChatById(long chatId);

    void removeChatById(long chatId);

    void removeChat(Chat chat);

    Chat findChatById(long chatId);

    List<Chat> findAllChats();
}
