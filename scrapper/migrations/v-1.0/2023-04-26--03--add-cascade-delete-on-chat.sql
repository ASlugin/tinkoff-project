--liquibase formatted sql

--changeset aslugin:drop constraint foreign key to chat
ALTER TABLE chat_link
DROP CONSTRAINT IF EXISTS chat_link_chat_id_fkey;

--changeset aslugin:add foreign key to chat with cascade delete
ALTER TABLE chat_link
ADD CONSTRAINT chat_link_chat_id_fkey FOREIGN KEY (chat_id) REFERENCES chat (id)
ON DELETE CASCADE;