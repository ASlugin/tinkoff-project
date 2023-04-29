package ru.tinkoff.edu.java.scrapper.configuration.access;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.persistence.repository.ChatRepository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jdbc.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jdbc.LinkRowMapper;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JdbcAccessConfiguration {
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public ChatRepository jdbcChatRepository() {
        return new JdbcChatRepository(jdbcTemplate);
    }

    @Bean
    public LinkRowMapper linkRowMapper() {
        return new LinkRowMapper();
    }

    @Bean
    public LinkRepository jdbclinkRepository() {
        return new JdbcLinkRepository(jdbcTemplate, linkRowMapper());
    }

    @Bean
    public LinkService jdbcLinkService() {
        return new JdbcLinkService(jdbclinkRepository());
    }

    @Bean
    public TgChatService jdbcTgChatService() {
        return new JdbcTgChatService(jdbcChatRepository());
    }
}
