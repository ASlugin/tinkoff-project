package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jdbc.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@Service
@RequiredArgsConstructor
@Slf4j
public class JdbcTgChatService implements TgChatService {
    private final JdbcChatRepository chatRepository;

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
