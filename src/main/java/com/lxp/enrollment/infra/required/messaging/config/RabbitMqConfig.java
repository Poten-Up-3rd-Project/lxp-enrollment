package com.lxp.enrollment.infra.required.messaging.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMqConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        template.setMandatory(true); //라우팅 실패 시 반환 받기
        template.setReturnsCallback(ret -> log.info(
            "Unroutable: exch={}, key={}, replyCode={}, replyText={}",
            ret.getExchange(), ret.getRoutingKey(), ret.getReplyCode(), ret.getReplyText()));
        template.setConfirmCallback((correlation, ack, cause) -> {
            if (!ack) {
                log.error("Publish NACK: correlation={}, cause={}", correlation, cause);
            } else {
                log.info("Publish ACK: {}", correlation);
            }
        });
        return template;
    }
}
