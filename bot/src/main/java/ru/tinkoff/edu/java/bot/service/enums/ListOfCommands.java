package ru.tinkoff.edu.java.bot.service.enums;

public enum ListOfCommands {
    START("/start", "Зарегистрировать пользователя"),
    HELP("/help", "Вывести окно с командами"),
    LIST("/list", "Показать список отслеживаемых ссылок"),
    TRACK("/track", "Начать отслеживание ссылки"),
    UNTRACK("/untrack", "Прекратить отслеживание ссылки");

    private final String command;
    private final String description;

    ListOfCommands(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public String getCommandWithDescription() {
        return command + " — " + description;
    }
}
