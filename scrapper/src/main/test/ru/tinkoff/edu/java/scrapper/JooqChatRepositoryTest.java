package ru.tinkoff.edu.java.scrapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.persistence.model.Chat;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jooq.JooqChatRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JooqChatRepositoryTest extends IntegrationEnvironment{
    @Autowired
    private JooqChatRepository chatRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    public void addChatById__queryChatFromDBShouldReturnAddedChat() {
        // given
        long id = 1234567L;

        // when
        chatRepository.addChatById(id);
        List<Chat> chats = jdbcTemplate.query("SELECT * FROM chat WHERE id=?",
                new BeanPropertyRowMapper<>(Chat.class), id);

        // then
        assertEquals(1, chats.size());
        assertEquals(id, chats.get(0).getId());
    }

    @Test
    @Transactional
    @Rollback
    public void removeChatById__chatShouldNotBeInDB() {
        // given
        long id = 1234567L;
        chatRepository.addChatById(id);

        // when
        chatRepository.removeChatById(id);
        List<Chat> chats = jdbcTemplate.query("SELECT * FROM chat WHERE id=?",
                new BeanPropertyRowMapper<>(Chat.class), id);

        // then
        assertEquals(0, chats.size());
    }

    @Test
    @Transactional
    @Rollback
    public void removeChat__chatShouldNotBeInDB() {
        // given
        long id = 1234567L;
        Chat chat = chatRepository.addChatById(id);

        // when
        chatRepository.removeChat(chat);
        List<Chat> chats = jdbcTemplate.query("SELECT * FROM chat WHERE id=?",
                new BeanPropertyRowMapper<>(Chat.class), id);

        // then
        assertEquals(0, chats.size());
    }

    @Test
    @Transactional
    @Rollback
    public void findAllChats__allChatsShouldBeReturned() {
        // given
        Long[] chatIds = new Long[]{123L, 456L, 777L};
        for (long chatId : chatIds) {
            chatRepository.addChatById(chatId);
        }

        // when
        List<Chat> chats = chatRepository.findAllChats();
        List<Long> resultChatIds = chats.stream().map(Chat::getId).toList();

        // then
        assertEquals(chatIds.length, chats.size());
        assertTrue(resultChatIds.containsAll(Arrays.stream(chatIds).toList()));
    }
}
