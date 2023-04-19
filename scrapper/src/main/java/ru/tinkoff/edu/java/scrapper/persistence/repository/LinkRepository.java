package ru.tinkoff.edu.java.scrapper.persistence.repository;

import ru.tinkoff.edu.java.scrapper.persistence.model.Link;

import java.net.URI;
import java.util.List;

public interface LinkRepository {
    Link addLink(long chatId, String url);
    Link removeLink(long chatId, String url);
    List<Link> findAllLinks(long chatId);
}
