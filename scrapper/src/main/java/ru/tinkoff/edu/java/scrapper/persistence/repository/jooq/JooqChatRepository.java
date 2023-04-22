package ru.tinkoff.edu.java.scrapper.persistence.repository.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.persistence.model.Chat;
import ru.tinkoff.edu.java.scrapper.persistence.repository.ChatRepository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JooqChatRepository implements ChatRepository {
    private final DSLContext dsl;
    @Override
    public Chat addChatById(long chatId) {
        dsl.insertInto(ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat.CHAT,
                ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat.CHAT.ID)
                .values(chatId)
                .execute();
        return findChatById(chatId);
    }

    @Override
    public void removeChatById(long chatId) {
        dsl.deleteFrom(ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat.CHAT)
                .where(ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat.CHAT.ID.eq(chatId))
                .execute();
    }

    @Override
    public void removeChat(Chat chat) {
        removeChatById(chat.getId());
    }

    @Override
    public Chat findChatById(long chatId) {
        try {
            return dsl.select().from(ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat.CHAT)
                    .where(ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat.CHAT.ID.eq(chatId))
                    .fetchOne().into(Chat.class);
        } catch (NullPointerException exc) {
            return null;
        }
    }

    @Override
    public List<Chat> findAllChats() {
        return dsl.select().from(ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat.CHAT).fetch().into(Chat.class);
    }
}
