package ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.entity;


import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "chat")
@Setter
@Getter
public class ChatEntity {
    @Id
    private Long id;

    @ManyToMany
    @JoinTable(name = "chat_link",
            joinColumns = @JoinColumn(name = "chat_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "link_id",
                    referencedColumnName = "id"))
    private List<LinkEntity> links;
}
