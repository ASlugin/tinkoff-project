package ru.tinkoff.edu.java.scrapper.persistence.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.persistence.model.Chat;
import ru.tinkoff.edu.java.scrapper.persistence.repository.ChatRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcChatRepository implements ChatRepository {
    private final JdbcTemplate jdbcTemplate;

    public Chat addChatById(long chatId) throws DuplicateKeyException {
        jdbcTemplate.update("INSERT INTO chat (id) VALUES (?)", chatId);
        return findChatById(chatId);
    }

    public void removeChatById(long chatId) {
        jdbcTemplate.update("DELETE FROM chat WHERE id=?", chatId);
    }

    public void removeChat(Chat chat) {
        removeChatById(chat.getId());
    }

    public Chat findChatById(long chatId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM chat WHERE id=?",
                    new BeanPropertyRowMapper<>(Chat.class), chatId);
        }
        catch (EmptyResultDataAccessException exc) {
            return null;
        }
    }

    public List<Chat> findAllChats() {
        return jdbcTemplate.query("SELECT * FROM chat", new BeanPropertyRowMapper<>(Chat.class));
    }
}
