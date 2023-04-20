package ru.tinkoff.edu.java.scrapper;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.persistence.model.Chat;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jdbc.JdbcChatRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ChatRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JdbcChatRepository chatRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    public void tablesTest() {
        chatRepository.addChatById(1234567L);
        List<Chat> chats = jdbcTemplate.query("SELECT * FROM chat WHERE id=?", new BeanPropertyRowMapper<>(Chat.class), 1234567L);
        assertEquals(1, chats.size());
    }
}
