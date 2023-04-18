--liquibase formatted sql

--changeset aslugin:add_column_check_at_to_link_table
ALTER TABLE link
ADD COLUMN checked_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();