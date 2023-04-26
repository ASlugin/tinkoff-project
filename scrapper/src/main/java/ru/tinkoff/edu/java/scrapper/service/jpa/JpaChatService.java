package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.entity.ChatEntity;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RequiredArgsConstructor
public class JpaChatService implements TgChatService {
    private final JpaChatRepository jpaChatRepository;

    @Override
    @Transactional
    public boolean register(long chatId) {
        if (isChatExist(chatId)) {
            return false;
        }
        ChatEntity newChat = new ChatEntity();
        newChat.setId(chatId);
        jpaChatRepository.save(newChat);
        return true;
    }

    @Override
    @Transactional
    public void unregister(long chatId) {
        jpaChatRepository.deleteById(chatId);
    }

    @Override
    public boolean isChatExist(long chatId) {
        return jpaChatRepository.existsById(chatId);
    }
}
