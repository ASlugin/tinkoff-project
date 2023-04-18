package ru.tinkoff.edu.java.scrapper.persistence.repository;

import ru.tinkoff.edu.java.scrapper.persistence.model.Link;

public interface LinkRepository {
    Link addLink();
    void removeLink();
    void findAllLinks();
}
