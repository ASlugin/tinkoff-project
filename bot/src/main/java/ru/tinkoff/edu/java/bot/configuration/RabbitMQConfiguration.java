package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfiguration {
    private final String exchange;
    private final String queue;
    private final String routingKey;

    public RabbitMQConfiguration(ApplicationConfig config) {
        exchange = config.rabbitMQ().exchange();
        queue = config.rabbitMQ().queue();
        routingKey = config.rabbitMQ().routingKey();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange, true, false);
    }

    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("user");
        cachingConnectionFactory.setPassword("password");
        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public ClassMapper classMapper(){
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.edu.java.scrapper.dto.client.LinkUpdateRequest", LinkUpdateRequest.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.edu.java.scrapper.dto.client.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        Jackson2JsonMessageConverter jsonConverter=new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper());
        return jsonConverter;
    }
}
