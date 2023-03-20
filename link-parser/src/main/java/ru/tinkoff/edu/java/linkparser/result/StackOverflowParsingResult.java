package ru.tinkoff.edu.java.linkparser.result;

public record StackOverflowParsingResult(String id) implements ParsingResult {
    @Override
    public String toString() {
        return "StackOverflow: " + "id=" + id;
    }
}
