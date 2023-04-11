--liquibase formatted sql

--changeset aslugin:create_chat_table
CREATE table chat (
    id SERIAL PRIMARY KEY
);

--changeset aslugin:create_link_table
CREATE table link (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    update_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

--changeset aslugin:create_chat_link_table
CREATE TABLE chat_link (
    chat_id INTEGER REFERENCES chat(id),
    link_id INTEGER REFERENCES link(id),
    PRIMARY KEY (chat_id, link_id)
)