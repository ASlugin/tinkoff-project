package ru.tinkoff.edu.java.scrapper.persistence.repository.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import ru.tinkoff.edu.java.scrapper.persistence.model.Chat;
import ru.tinkoff.edu.java.scrapper.persistence.repository.ChatRepository;
import java.util.List;

import static ru.tinkoff.edu.java.scrapper.persistence.jooq.tables.Chat.CHAT;

@RequiredArgsConstructor
public class JooqChatRepository implements ChatRepository {
    private final DSLContext dsl;
    @Override
    public Chat addChatById(long chatId) {
        dsl.insertInto(CHAT, CHAT.ID)
                .values(chatId)
                .execute();
        return findChatById(chatId);
    }

    @Override
    public void removeChatById(long chatId) {
        dsl.deleteFrom(CHAT)
                .where(CHAT.ID.eq(chatId))
                .execute();
    }

    @Override
    public void removeChat(Chat chat) {
        removeChatById(chat.getId());
    }

    @Override
    public Chat findChatById(long chatId) {
        var result = dsl.select().from(CHAT)
                    .where(CHAT.ID.eq(chatId))
                    .fetchOne();
        if (result != null) {
            return result.into(Chat.class);
        }
        return null;
    }

    @Override
    public List<Chat> findAllChats() {
        return dsl.select().from(CHAT).fetch().into(Chat.class);
    }
}
