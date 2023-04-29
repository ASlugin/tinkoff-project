package ru.tinkoff.edu.java.scrapper.persistence.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.persistence.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.persistence.model.Link;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.entity.ChatEntity;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinkService;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JpaLinkServiceTest extends IntegrationEnvironment {
    @Autowired
    private JpaLinkService jpaLinkService;
    @Autowired
    private JpaChatService jpaChatService;
    @Autowired
    private JpaLinkRepository jpaLinkRepository;
    @Autowired
    private JpaChatRepository jpaChatRepository;

    private static final Long[] chatIds = new Long[]{1L, 2L, 3L};
    public static final String[] links = {"https://github.com/ASlugin/tinkoff-project",
            "https://stackoverflow.com/questions/61074125/spring-boot-test-does-not-start-context-or-load-dependencies"};

    private void registerChats() {
        for (long chatId : chatIds) {
            jpaChatService.register(chatId);
        }
    }
    @Test
    @Transactional
    @Rollback
    public void add_listAll__addLinksToEveryChats__eachChatShouldHaveAllAddedLinks() {
        // given
        registerChats();

        // when
        for (long chatId : chatIds) {
            for (String link : links) {
                jpaLinkService.add(chatId, URI.create(link));
            }
        }

        // then
        for (long chatId : chatIds) {
            List<Link> listOfLinks = jpaLinkService.listAll(chatId);
            for (String link : links) {
                assertTrue(listOfLinks.stream().map(l -> l.getUrl()).toList().contains(link));
            }
            assertEquals(links.length, listOfLinks.size());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void remove__removeLinkForChat__chatShouldNotHaveDeletedLink() {
        // given
        registerChats();
        for (long chatId : chatIds) {
            for (String link : links) {
                jpaLinkService.add(chatId, URI.create(link));
            }
        }
        long removeChatId = chatIds[0];
        String removeLinkUrl = links[0];
        ChatEntity chatEntity = jpaChatRepository.findById(removeChatId).get();

        // when
        jpaLinkService.remove(removeChatId, URI.create(removeLinkUrl));

        // then
        assertTrue(jpaLinkRepository.findByUrlAndChatsContains(removeLinkUrl, chatEntity).isEmpty());
    }
}
