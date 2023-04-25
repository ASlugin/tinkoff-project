package ru.tinkoff.edu.java.scrapper.persistence.repository.jpa;

import ru.tinkoff.edu.java.scrapper.persistence.model.Chat;
import ru.tinkoff.edu.java.scrapper.persistence.repository.ChatRepository;

import java.util.List;

public class JpaChatRepository implements ChatRepository {
    @Override
    public Chat addChatById(long chatId) {
        return null;
    }

    @Override
    public void removeChatById(long chatId) {

    }

    @Override
    public void removeChat(Chat chat) {

    }

    @Override
    public Chat findChatById(long chatId) {
        return null;
    }

    @Override
    public List<Chat> findAllChats() {
        return null;
    }
}
