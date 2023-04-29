package ru.tinkoff.edu.java.scrapper.persistence.jpa;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.tinkoff.edu.java.scrapper.persistence.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaChatService;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JpaChatServiceTest extends IntegrationEnvironment {
    @Autowired
    private JpaChatService jpaChatService;

    @Test
    public void register__registerChat__chatShouldExistInDB() {
        // given
        long chatId = 12345L;

        // when
        jpaChatService.register(chatId);

        //then
        assertTrue(jpaChatService.isChatExist(chatId));
    }

    @Test
    public void unregister__unregisterChat__chatShouldNotExistInDB() {
        // given
        long chatId = 12345L;
        jpaChatService.register(chatId);

        // when
        jpaChatService.unregister(chatId);

        //then
        assertFalse(jpaChatService.isChatExist(chatId));
    }
}
