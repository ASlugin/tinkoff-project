package ru.tinkoff.edu.java.linkparser.result;

public record GithubParsingResult(String user, String repository) implements ParsingResult {
    @Override
    public String toString() {
        return "Github: " + "user=" + user + ", " + "repository=" + repository;
    }
}
