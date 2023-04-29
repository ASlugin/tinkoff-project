package ru.tinkoff.edu.java.scrapper.service.jooq;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.persistence.model.Link;
import ru.tinkoff.edu.java.scrapper.persistence.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private final LinkRepository linkRepository;

    @Override
    public Link add(long tgChatId, URI url) {
        return linkRepository.addLink(tgChatId, url.toString());
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        return linkRepository.removeLink(tgChatId, url.toString());
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        return linkRepository.findAllLinksForChat(tgChatId);
    }
}
