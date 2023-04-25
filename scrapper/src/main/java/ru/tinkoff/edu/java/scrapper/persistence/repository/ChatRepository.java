package ru.tinkoff.edu.java.scrapper.persistence.repository;

import ru.tinkoff.edu.java.scrapper.persistence.model.Chat;

import java.util.List;

public interface ChatRepository {
    Chat addChatById(long chatId);
    void removeChatById(long chatId);
    void removeChat(Chat chat);
    Chat findChatById(long chatId);
    List<Chat> findAllChats();
}
