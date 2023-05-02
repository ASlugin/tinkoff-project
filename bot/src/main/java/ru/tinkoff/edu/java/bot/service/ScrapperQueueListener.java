package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.service.UserMessageProcessor;

import java.util.*;

@Component
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class ScrapperQueueListener {
    private final RabbitTemplate rabbitTemplate;
    private final UserMessageProcessor messageProcessor;
    private final ApplicationConfig config;

    @RabbitListener(queues = "${app.rabbitMQ.queue}")
    public void receiver(LinkUpdateRequest updateRequest) {
        log.info("Update link by Rabbit");
        messageProcessor.sendUpdateMessages(updateRequest);
    }

    @RabbitListener(queues = "${app.rabbitMQ.queue}.dlq")
    public void processFailedMessages(Message message) {
        Integer messageXDeathCount = null;
        if (message.getMessageProperties() != null && message.getMessageProperties().getHeaders() != null) {
            List<Map<String, ?>> xDeathHeader = (List<Map<String, ?>>) message
                    .getMessageProperties()
                    .getHeaders()
                    .get("x-death");
            if (xDeathHeader != null && xDeathHeader.get(0) != null) {
                messageXDeathCount = (Integer) xDeathHeader.get(0).get("count");
            }
        }
        if (messageXDeathCount == null || messageXDeathCount > 5) {
            log.info("Discarding message");
        }
        else {
            rabbitTemplate.send(config.rabbitMQ().exchange(), config.rabbitMQ().routingKey(), message);
        }
    }
}
