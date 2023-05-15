package ru.tinkoff.edu.java.scrapper.persistence.repository;

import java.time.OffsetDateTime;
import java.util.List;
import ru.tinkoff.edu.java.scrapper.persistence.model.Link;

public interface LinkRepository {
    Link addLink(long chatId, String url);

    Link removeLink(long chatId, String url);

    List<Link> findAllLinksForChat(long chatId);

    List<Link> findNotCheckedLinks(int checkIntervalMinutes);

    // Returns list of chat id
    List<Long> updateLink(Link link, OffsetDateTime updatedAt);

    void checkLink(Link link);
}
