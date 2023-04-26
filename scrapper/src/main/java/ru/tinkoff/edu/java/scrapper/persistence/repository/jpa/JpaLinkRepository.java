package ru.tinkoff.edu.java.scrapper.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.entity.ChatEntity;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.entity.LinkEntity;

import java.util.List;
import java.util.Optional;


@Repository
public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {
    List<LinkEntity> findAllByChatsContains(ChatEntity chatEntity);

    Optional<LinkEntity> findByUrl(String url);

    Optional<LinkEntity> findByUrlAndChatsContains(String url, ChatEntity chatEntity);

}
