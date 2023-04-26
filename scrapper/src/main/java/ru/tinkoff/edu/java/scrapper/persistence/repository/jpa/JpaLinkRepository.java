package ru.tinkoff.edu.java.scrapper.persistence.repository.jpa;

import ru.tinkoff.edu.java.scrapper.persistence.model.Link;
import ru.tinkoff.edu.java.scrapper.persistence.repository.LinkRepository;

import java.time.OffsetDateTime;
import java.util.List;

public class JpaLinkRepository implements LinkRepository {
    @Override
    public Link addLink(long chatId, String url) {
        return null;
    }

    @Override
    public Link removeLink(long chatId, String url) {
        return null;
    }

    @Override
    public List<Link> findAllLinksForChat(long chatId) {
        return null;
    }

    @Override
    public List<Link> findNotCheckedLinks(int checkIntervalMinutes) {
        return null;
    }

    @Override
    public List<Long> updateLink(Link link, OffsetDateTime updatedAt) {
        return null;
    }

    @Override
    public void checkLink(Link link) {

    }
}
