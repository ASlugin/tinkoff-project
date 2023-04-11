--liquibase formatted sql

--changeset aslugin:create_chat_table
CREATE table chat (
    id SERIAL PRIMARY KEY
);

--changeset aslugin:create_link_table
CREATE table link (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    update_at TIMESTAMP with time zone DEFAULT now(),
    chat_id INTEGER REFERENCES chat (id)
);