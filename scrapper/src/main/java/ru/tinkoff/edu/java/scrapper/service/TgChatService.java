package ru.tinkoff.edu.java.scrapper.service;

public interface TgChatService {
    boolean register(long chatId);
    void unregister(long chatId);
    boolean isChatExist(long chatId);
}
