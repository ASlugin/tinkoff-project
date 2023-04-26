package ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat")
@Setter
public class ChatEntity {
    @Id
    private Long id;
}
