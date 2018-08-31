package com.my.rabbit.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author jim lin
 * 2018/8/30.
 */
@Configuration
@Import(RabbitConfiguration.class)
public class HeadersQueueConfiguration {


    private static final String QUEUE_HEADERS = "queue_headers";
    private static final String HEADERS_EXCHANGE = "headers_exchange";
    private static final String HEADERS_KEY = "headers.key";

    @Bean(name="headersRabbitTemplate")
    public RabbitTemplate getHeadersRabbitTemplate(ConnectionFactory connectionFactory,
                                                   MessageConverter messageConverter, MessagePropertiesConverter messagePropertiesConverter)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setMessagePropertiesConverter(messagePropertiesConverter);
        rabbitTemplate.setReceiveTimeout(6000);
        rabbitTemplate.setExchange(HEADERS_EXCHANGE);
        return rabbitTemplate;
    }

    @Bean
    public Queue headersA(){
        return new Queue(QUEUE_HEADERS,true);
    }

    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(HEADERS_EXCHANGE,true,false);
    }
    @Bean
    public Binding buildBindingHeadersA(){
        return BindingBuilder.bind(headersA()).to(headersExchange()).where(HEADERS_KEY).matches("123");
    }
    @Bean
    public Binding buildBindingHeadersB(){
        String[] keys = {"key1","key2","key3"};
        return BindingBuilder.bind(headersA()).to(headersExchange()).whereAll(keys).exist();
    }

    @Bean
    public Binding buildBindingHeadersC(){
        String[] keys = {"key4","key5","key6"};
        return BindingBuilder.bind(headersA()).to(headersExchange()).whereAny(keys).exist();
    }


}
