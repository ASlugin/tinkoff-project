package ru.tinkoff.edu.java.scrapper.persistence.repository.jpa;

import java.util.List;
import java.util.Optional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.entity.ChatEntity;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.entity.LinkEntity;



@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {
    List<LinkEntity> findAllByChatsContains(ChatEntity chatEntity);

    Optional<LinkEntity> findByUrl(String url);

    Optional<LinkEntity> findByUrlAndChatsContains(String url, ChatEntity chatEntity);

}
