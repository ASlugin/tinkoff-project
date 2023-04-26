package ru.tinkoff.edu.java.scrapper.persistence.repository.jpa.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "link")
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "checked_at")
    private OffsetDateTime checkedAt;
}
