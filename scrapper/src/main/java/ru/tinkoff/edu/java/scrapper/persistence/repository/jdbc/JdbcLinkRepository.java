package ru.tinkoff.edu.java.scrapper.persistence.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.persistence.model.Link;
import ru.tinkoff.edu.java.scrapper.persistence.repository.LinkRepository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository implements LinkRepository {
    private final JdbcTemplate jdbcTemplate;
    private final LinkRowMapper linkRowMapper;

    @Override
    public Link addLink(long chatId, String url) {
        Link link = findLinkByUrl(url);
        if (link == null) {
            return addNewLink(chatId, url);
        }
        if (!relationExists(chatId, link.getId())) {
            jdbcTemplate.update("INSERT INTO chat_link (chat_id, link_id) VALUES (?, ?)", chatId, link.getId());
        }
        return link;
    }

    @Override
    public void removeLink(long chatId, String url) {

    }

    @Override
    public List<Link> findAllLinks(long chatId) {
        return jdbcTemplate.query("SELECT id, url, updated_at, checked_at FROM link " +
                        "JOIN chat_link ON link.id=chat_link.link_id " +
                        "WHERE chat_link.chat_id=?",
                linkRowMapper, chatId);
    }

    @Transactional
    protected Link addNewLink(long chatId, String url) {
        jdbcTemplate.update("INSERT INTO link (url) VALUES (?)", url);
        Link link = findLinkByUrl(url);
        jdbcTemplate.update("INSERT INTO chat_link (chat_id, link_id) VALUES (?, ?)", chatId, Objects.requireNonNull(link).getId());
        return link;
    }

    private boolean relationExists(long chatId, long linkId) {
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM chat_link WHERE chat_id=? AND link_id=?",
                Integer.class, chatId, linkId);
        return count != null && count != 0;
    }

    private Link findLinkByUrl(String url) {
        try {
            return jdbcTemplate.queryForObject("SELECT id, url, updated_at, checked_at FROM link WHERE url=?",
                    linkRowMapper, url);
        }
        catch (EmptyResultDataAccessException exc) {
            return null;
        }
    }
}
