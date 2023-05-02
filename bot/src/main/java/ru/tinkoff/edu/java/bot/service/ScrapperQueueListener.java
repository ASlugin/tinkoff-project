package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;

@Component
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
@RequiredArgsConstructor
@RabbitListener(queues = "${app.rabbitMQ.queue}")
@Slf4j
public class ScrapperQueueListener {
    private final UserMessageProcessor messageProcessor;

    @RabbitHandler
    public void receiver(LinkUpdateRequest updateRequest) {
        log.info("Update link by Rabbit");
        messageProcessor.sendUpdateMessages(updateRequest);
    }
}
