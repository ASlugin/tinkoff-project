package ru.tinkoff.edu.java.scrapper.service.jooq;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.persistence.model.Chat;
import ru.tinkoff.edu.java.scrapper.persistence.repository.ChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@Service
@RequiredArgsConstructor
public class JooqChatService implements TgChatService {
    @Qualifier("jooqChatRepository")
    private final ChatRepository chatRepository;

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
        Chat chat = chatRepository.findChatById(chatId);
        return chat != null;
    }
}
