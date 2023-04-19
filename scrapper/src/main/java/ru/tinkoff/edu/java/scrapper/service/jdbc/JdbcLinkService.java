package ru.tinkoff.edu.java.scrapper.service.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.persistence.model.Link;
import ru.tinkoff.edu.java.scrapper.persistence.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.List;

@Service
public class JdbcLinkService implements LinkService {
    private final LinkRepository linkRepository;

    public JdbcLinkService(@Qualifier("jdbcLinkRepository") LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

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
        return linkRepository.findAllLinks(tgChatId);
    }

}
