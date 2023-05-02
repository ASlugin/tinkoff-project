package ru.tinkoff.edu.java.scrapper.service.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.dto.client.LinkUpdateRequest;

@Service
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class QueueProducer implements LinkUpdateProducer {
    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public QueueProducer(RabbitTemplate rabbitTemplate, ApplicationConfig config) {
        this.rabbitTemplate = rabbitTemplate;
        exchange = config.rabbitMQ().exchange();
        routingKey = config.rabbitMQ().routingKey();
    }

    @Override
    public void send(LinkUpdateRequest updateRequest) {
        rabbitTemplate.convertAndSend(exchange, routingKey, updateRequest);
    }
}
