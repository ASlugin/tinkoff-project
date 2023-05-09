package ru.tinkoff.edu.java.scrapper.persistence.repository.jooq;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.persistence.model.Link;
import ru.tinkoff.edu.java.scrapper.persistence.repository.LinkRepository;
import static org.jooq.impl.DSL.*;
import static ru.tinkoff.edu.java.scrapper.persistence.jooq.tables.ChatLink.CHAT_LINK;
import static ru.tinkoff.edu.java.scrapper.persistence.jooq.tables.Link.LINK;

@RequiredArgsConstructor
public class JooqLinkRepository implements LinkRepository {
    private final DSLContext dsl;

    @Override
    public Link addLink(long chatId, String url) {
        Link link = findLinkByUrl(url);
        if (link == null) {
            return addNewLink(chatId, url);
        }
        if (!relationExists(chatId, link.getId())) {
            dsl.insertInto(CHAT_LINK, CHAT_LINK.CHAT_ID, CHAT_LINK.LINK_ID)
                    .values(chatId, link.getId())
                    .execute();
        }
        return link;
    }

    @Transactional
    protected Link addNewLink(long chatId, String url) {
        dsl.insertInto(LINK, LINK.URL)
                .values(url)
                .execute();
        Link link = findLinkByUrl(url);
        dsl.insertInto(CHAT_LINK, CHAT_LINK.CHAT_ID, CHAT_LINK.LINK_ID)
                .values(chatId, link.getId())
                .execute();
        return link;
    }

    private boolean relationExists(long chatId, long linkId) {
        int count = dsl.select()
                .from(CHAT_LINK)
                .where(CHAT_LINK.CHAT_ID.eq(chatId), CHAT_LINK.LINK_ID.eq(linkId))
                .execute();
        return count != 0;
    }

    @Override
    public Link removeLink(long chatId, String url) {
        Link link = findLinkByUrl(url);
        if (link == null) {
            return null;
        }
        dsl.delete(CHAT_LINK)
                .where(CHAT_LINK.CHAT_ID.eq(chatId), CHAT_LINK.LINK_ID.eq(link.getId()))
                .execute();
        return link;
    }

    @Override
    public List<Link> findAllLinksForChat(long chatId) {
        return dsl.select()
                .from(LINK)
                .join(CHAT_LINK)
                .on(CHAT_LINK.LINK_ID.eq(LINK.ID))
                .where(CHAT_LINK.CHAT_ID.eq(chatId))
                .fetch()
                .into(Link.class);
    }

    @Override
    public List<Link> findNotCheckedLinks(int checkIntervalMinutes) {
//        return dsl.select()
//                .from(LINK)
//                .where(DSL.timestampDiff(LINK.CHECKED_AT, DSL.currentTimestamp().sub(checkIntervalMinutes))
//                .ge(new DayToSecond()) )
//                .fetch()
//                .into(Link.class);
        return null;
    }

    @Override
    public List<Long> updateLink(Link link, OffsetDateTime updatedAt) {
        dsl.update(LINK)
                .set(LINK.UPDATED_AT, updatedAt)
                .set(LINK.CHECKED_AT, currentOffsetDateTime())
                .where(LINK.ID.eq(link.getId()))
                .execute();

        return dsl.select(CHAT_LINK.CHAT_ID)
                .from(CHAT_LINK)
                .where(CHAT_LINK.LINK_ID.eq(link.getId()))
                .fetch()
                .into(Long.class);
    }

    @Override
    public void checkLink(Link link) {
        dsl.update(LINK)
                .set(LINK.CHECKED_AT, currentOffsetDateTime())
                .where(LINK.ID.eq(link.getId()))
                .execute();
    }

    public Link findLinkByUrl(String url) {
        var result = dsl.select().from(LINK)
                    .where(LINK.URL.eq(url))
                    .fetchOne();
        if (result != null) {
            return result.into(Link.class);
        }
        return null;
    }
}
