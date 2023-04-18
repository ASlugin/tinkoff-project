package ru.tinkoff.edu.java.scrapper.service.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.persistence.repository.ChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@Service
public class JdbcTgChatService implements TgChatService {
    private final ChatRepository chatRepository;

    public JdbcTgChatService(@Qualifier("jdbcChatRepository") ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public boolean register(long chatId) {
        try {
            chatRepository.addChatById(chatId);
            return true;
        } catch (DuplicateKeyException exc) {
            return false;
        }
    }

    @Override
    public void unregister(long chatId) {
        chatRepository.removeChatById(chatId);
    }

    @Override
    public boolean isChatExist(long chatId) {
        return chatRepository.findChatById(chatId) != null;
    }
}
