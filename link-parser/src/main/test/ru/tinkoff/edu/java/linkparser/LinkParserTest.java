package ru.tinkoff.edu.java.linkparser;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.tinkoff.edu.java.linkparser.result.GitHubParsingResult;
import ru.tinkoff.edu.java.linkparser.result.ParsingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LinkParserTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "https://github.com/ASlugin/",
            "https://stackoverflow.com/questions/",
            "https://google.com",
            "ololo",
    })
    void parserShallReturnNullIfLinkIsUnsupportedTest(String link) {
        ParsingResult result = LinkParser.parse(link);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({
            "https://github.com/ASlugin/tinkoff-project/pull/3," + "ASlugin," + "tinkoff-project",
            "https://github.com/i1ya/spbu_se_site," + "i1ya," + "spbu_se_site",
            "https://github.com/i1ya/spbu_se_site/," + "i1ya," + "spbu_se_site",
    })
    void parseCorrectGithubLinksShallWorkCorrectlyTest(String link, String expectedUser, String expectedRepo) {
        ParsingResult result = LinkParser.parse(link);
        assertEquals("Github: " + "user=" + expectedUser + ", " + "repository=" + expectedRepo, result.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "https://stackoverflow.com/questions/19580197," + "19580197",
            "https://stackoverflow.com/questions/14563741/," + "14563741",
            "https://stackoverflow.com/questions/8408892/cast-an-object-to-an-interface-in-java," + "8408892"
    })
    void parseCorrectStackOverflowLinksShallWorkCorrectlyTest(String link, String expectedId) {
        ParsingResult result = LinkParser.parse(link);
        assertEquals("StackOverflow: " + "id=" + expectedId, result.toString());
    }

}
