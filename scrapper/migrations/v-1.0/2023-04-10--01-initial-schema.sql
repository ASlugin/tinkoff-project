--liquibase formatted sql

--changeset aslugin:create_chat_table
CREATE TABLE IF NOT EXISTS chat (
    id BIGSERIAL PRIMARY KEY
);

--changeset aslugin:create_link_table
CREATE TABLE IF NOT EXISTS link (
    id BIGSERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

--changeset aslugin:create_chat_link_table
CREATE TABLE IF NOT EXISTS chat_link (
    chat_id BIGINT REFERENCES chat(id),
    link_id BIGINT REFERENCES link(id),
    PRIMARY KEY (chat_id, link_id)
)