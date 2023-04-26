package ru.tinkoff.edu.java.scrapper.configuration.access;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.persistence.repository.ChatRepository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jooq.JooqChatRepository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqChatService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
@RequiredArgsConstructor
public class JooqAccessConfiguration {
    private final DSLContext dslContext;

    @Bean
    public ChatRepository jooqChatRepository() {
        return new JooqChatRepository(dslContext);
    }

    @Bean
    public LinkRepository jooqLinkRepository() {
        return new JooqLinkRepository(dslContext);
    }

    @Bean
    public TgChatService jooqChatService() {
        return new JooqChatService(jooqChatRepository());
    }

    @Bean
    public LinkService jooqLinkService() {
        return new JooqLinkService(jooqLinkRepository());
    }
}
