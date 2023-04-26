package ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "link")
@Getter
@Setter
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "checked_at")
    private OffsetDateTime checkedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "chat_link",
            joinColumns = @JoinColumn(name = "link_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id",
                    referencedColumnName = "id"))
    private List<ChatEntity> chats;

    public void removeChat(ChatEntity chatEntity) {
        chats.remove(chatEntity);
    }
}
