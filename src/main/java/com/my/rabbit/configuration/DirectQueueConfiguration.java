package com.my.rabbit.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
public class DirectQueueConfiguration {

    private static final String QUEUE_DIRECT_A = "queue_direct_A";
    private static final String DIRECT_EXCHANGE = "direct_exchange";
    private static final String DIRECT_ROUTING_KEY_A = "direct.routing.keyA";

    @Bean(name="directRabbitTemplate")
    public RabbitTemplate getHeadersRabbitTemplate(ConnectionFactory connectionFactory,
                                                   MessageConverter messageConverter, MessagePropertiesConverter messagePropertiesConverter)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setMessagePropertiesConverter(messagePropertiesConverter);
        rabbitTemplate.setReceiveTimeout(6000);
        rabbitTemplate.setExchange(DIRECT_EXCHANGE);
        rabbitTemplate.setRoutingKey(DIRECT_ROUTING_KEY_A);
        return rabbitTemplate;
    }
    @Bean
    public Queue directA(){
        return new Queue(QUEUE_DIRECT_A,true);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(DIRECT_EXCHANGE,true,false);
    }
    @Bean
    public Binding buildBindingDirectA(){
        return BindingBuilder.bind(directA()).to(directExchange()).with(DIRECT_ROUTING_KEY_A);
    }
}
