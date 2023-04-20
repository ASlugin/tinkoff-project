package ru.tinkoff.edu.java.scrapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jdbc.JdbcChatRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SomeTest {
    @Autowired
    private JdbcChatRepository jdbcChatRepository;

    @Test
    public void test() throws Exception {
        jdbcChatRepository.addChatById(4560L);
        assertTrue(1 == 1);
    }
}
