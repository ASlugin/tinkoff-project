package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.tinkoff.edu.java.linkparser.LinkParser;
import ru.tinkoff.edu.java.linkparser.result.GitHubParsingResult;
import ru.tinkoff.edu.java.linkparser.result.ParsingResult;
import ru.tinkoff.edu.java.linkparser.result.StackOverflowParsingResult;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.client.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.client.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.dto.client.StackOverflowItem;
import ru.tinkoff.edu.java.scrapper.dto.client.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.persistence.model.Link;
import ru.tinkoff.edu.java.scrapper.persistence.repository.LinkRepository;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final LinkRepository linkRepository;
    private final BotClient botClient;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    @Value("${check-link-interval-minutes}")
    private int checkLinkInterval;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        log.info("Find links for update");

        List<Link> links = linkRepository.findNotCheckedLinks(checkLinkInterval);
        for (Link link : links) {

            // По-хорошему бы это вынести в какой-нибудь сервис и в нем реализовать chain of responsibility
            ParsingResult parsingLink = LinkParser.parse(link.getUrl());
            if (parsingLink instanceof GitHubParsingResult githubLink) {
                try {
                    GitHubResponse response = gitHubClient.fetchRepository(githubLink.user(), githubLink.repository()).block();
                    if (response == null) {
                        continue;
                    }
                    if (!response.timeOfLastUpdate().isEqual(link.getUpdatedAt())) {
                        updateLinkAndSendNotifications(link, response.timeOfLastUpdate());
                    }
                    else {
                        linkRepository.checkLink(link);
                    }

                }
                catch (WebClientResponseException exc) {
                    log.error(exc.getMessage());
                }
            }
            else if (parsingLink instanceof StackOverflowParsingResult stackOverflowLink) {
                try {
                    StackOverflowResponse response = stackOverflowClient.fetchQuestion(stackOverflowLink.id()).block();
                    StackOverflowItem responseLink = response == null ? null : response.item().stream()
                            .filter(item -> ((Long) item.questionId()).toString().equals((stackOverflowLink.id())))
                            .findAny()
                            .orElse(null);
                    if (responseLink == null) {
                        continue;
                    }
                    if (!responseLink.lastActivityDate().isEqual(link.getUpdatedAt())) {
                        updateLinkAndSendNotifications(link, responseLink.lastActivityDate());
                    }
                    else {
                        linkRepository.checkLink(link);
                    }
                }
                catch (WebClientResponseException exc) {
                    log.error(exc.getMessage());
                }
            }

        }
    }

    private void updateLinkAndSendNotifications(Link link, OffsetDateTime updateAt) {
        List<Long> chatIds = linkRepository.updateLink(link, updateAt);
        botClient.updates(new LinkUpdateRequest(link.getId(),
                URI.create(link.getUrl()),
                "Появились изменения по ссылке " + link.getUrl(),
                chatIds.stream().mapToLong(x -> x).toArray()));
    }
}
