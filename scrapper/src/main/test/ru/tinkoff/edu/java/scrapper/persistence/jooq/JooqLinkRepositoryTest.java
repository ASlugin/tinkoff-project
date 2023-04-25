package ru.tinkoff.edu.java.scrapper.persistence.jooq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.persistence.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.persistence.model.Link;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jdbc.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jooq.JooqLinkRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JooqLinkRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JooqLinkRepository linkRepository;
    @Autowired
    private JdbcChatRepository chatRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Long[] chatIds = new Long[]{1L, 2L, 3L};
    public static final String[] links = {"https://github.com/ASlugin/tinkoff-project",
            "https://stackoverflow.com/questions/61074125/spring-boot-test-does-not-start-context-or-load-dependencies"};

    private void addChats() {
        for (long chatId : chatIds) {
            chatRepository.addChatById(chatId);
        }
    }

    @Test
    @Transactional
    @Rollback
    public void addLink__addLinksToEveryChats__eachChatShouldHaveCorrectAmountOfLinks() {
        // given
        addChats();

        // when
        for (long chatId : chatIds) {
            for (String link : links) {
                linkRepository.addLink(chatId, link);
            }
        }

        // then
        for (long chatId : chatIds) {
            Integer amountOfLinksForChat = jdbcTemplate.queryForObject("SELECT count(*) FROM chat_link WHERE chat_id=?",
                    Integer.class, chatId);
            assertEquals(links.length, amountOfLinksForChat.intValue());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void addLink__addSameLink__inDBShouldBeOnlyOneLink() {
        // given
        addChats();

        // when
        for (long chatId : chatIds) {
            linkRepository.addLink(chatId, links[0]);
        }

        // then
        Integer amountOfLinks = jdbcTemplate.queryForObject("SELECT count(*) FROM link", Integer.class);
        assertEquals(1, amountOfLinks.intValue());
    }

    @Test
    @Transactional
    @Rollback
    public void removeLink__shouldNotExistRelationBetweenChatAndLink() {
        // given
        addChats();
        for (long chatId : chatIds) {
            for (String link : links) {
                linkRepository.addLink(chatId, link);
            }
        }
        long removeChatId = chatIds[0];
        String removeLinkUrl = links[0];

        // when
        Link removedLink = linkRepository.removeLink(removeChatId, removeLinkUrl);

        // then
        Integer result = jdbcTemplate.queryForObject("SELECT count(*) FROM chat_link WHERE chat_id=? AND link_id=?",
                Integer.class, removeChatId, removedLink.getId());
        assertEquals(0, result);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllLinksForChat__inDBExistAllAddedLinkForChat() {
        // given
        addChats();
        for (long chatId : chatIds) {
            for (String link : links) {
                linkRepository.addLink(chatId, link);
            }
        }
        long chatId = chatIds[0];

        // when
        List<Link> resultLinks = linkRepository.findAllLinksForChat(chatId);
        List<String> resultUrls = resultLinks.stream().map(Link::getUrl).toList();

        // then
        assertTrue(resultUrls.containsAll(Arrays.stream(links).toList()));
    }
}
