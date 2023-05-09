package ru.tinkoff.edu.java.scrapper.service.jpa;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.persistence.model.Link;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.entity.ChatEntity;
import ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.entity.LinkEntity;
import ru.tinkoff.edu.java.scrapper.service.LinkService;


@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository jpaLinkRepository;
    private final JpaChatRepository jpaChatRepository;

    @Override
    @Transactional
    public Link add(long tgChatId, URI url) {
        Optional<ChatEntity> chatEntityOptional = jpaChatRepository.findById(tgChatId);
        if (chatEntityOptional.isEmpty()) {
            return null;
        }
        ChatEntity chat = chatEntityOptional.get();

        Optional<LinkEntity> linkOptional = jpaLinkRepository.findByUrl(url.toString());
        if (linkOptional.isPresent()) {
            LinkEntity linkEntity = linkOptional.get();
            linkEntity.getChats().add(chat);
            jpaLinkRepository.save(linkEntity);
            return new Link(linkEntity.getId(), linkEntity.getUrl(), linkEntity.getUpdatedAt(),
                linkEntity.getCheckedAt());
        }

        LinkEntity newLink = new LinkEntity();
        newLink.setUrl(url.toString());
        List<ChatEntity> chats = new ArrayList<>();
        chats.add(chat);
        newLink.setChats(chats);
        newLink.setCheckedAt(OffsetDateTime.now());
        jpaLinkRepository.save(newLink);
        return new Link(newLink.getId(), newLink.getUrl(), newLink.getUpdatedAt(), newLink.getCheckedAt());
    }

    @Override
    @Transactional
    public Link remove(long tgChatId, URI url) {
        Optional<ChatEntity> chatOptional = jpaChatRepository.findById(tgChatId);
        if (chatOptional.isEmpty()) {
            return null;
        }
        ChatEntity chat = chatOptional.get();

        Optional<LinkEntity> linkOptional = jpaLinkRepository.findByUrlAndChatsContains(url.toString(), chat);
        if (linkOptional.isEmpty()) {
            return null;
        }
        LinkEntity linkEntity = linkOptional.get();
        linkEntity.removeChat(chat);
        jpaLinkRepository.save(linkEntity);
        return new Link(linkEntity.getId(), linkEntity.getUrl(), linkEntity.getUpdatedAt(), linkEntity.getCheckedAt());
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        var chat = jpaChatRepository.findById(tgChatId);
        if (chat.isEmpty()) {
            return new ArrayList<>();
        }
        List<LinkEntity> linkEntities = jpaLinkRepository.findAllByChatsContains(chat.get());
        return linkEntities.stream()
                .map(linkEntity -> new Link(linkEntity.getId(), linkEntity.getUrl(),
                        linkEntity.getUpdatedAt(), linkEntity.getCheckedAt()))
                .toList();
    }
}
