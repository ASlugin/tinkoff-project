package ru.tinkoff.edu.java.scrapper.persistence.model;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class Link {
    private long id;
    private String url;
    private OffsetDateTime updatedAt;
    private OffsetDateTime checkedAt;

    public Link(long id, String url, OffsetDateTime updatedAt, OffsetDateTime checkedAt) {
        this.id = id;
        this.url = url;
        this.updatedAt = updatedAt;
        this.checkedAt = checkedAt;
    }

    public Link() {

    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = OffsetDateTime.ofInstant(updatedAt.toInstant(), ZoneId.of("UTC"));
    }

    public void setCheckedAt(Timestamp checkedAt) {
        this.updatedAt = OffsetDateTime.ofInstant(checkedAt.toInstant(), ZoneId.of("UTC"));
    }

    public OffsetDateTime getCheckedAt() {
        return checkedAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
