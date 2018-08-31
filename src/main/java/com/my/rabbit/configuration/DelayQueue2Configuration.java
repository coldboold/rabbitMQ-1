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
public class DelayQueue2Configuration {

    private static final String QUEUE_DELAY2 = "queue_delay2";
    private static final String DELAY2_EXCHANGE = "delay2_exchange";
    private static final String DELAY2_ROUTING_KEY = "delay2.routing.key";

    @Bean(name="delay2RabbitTemplate")
    public RabbitTemplate getHeadersRabbitTemplate(ConnectionFactory connectionFactory,
                                                   MessageConverter messageConverter, MessagePropertiesConverter messagePropertiesConverter)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setMessagePropertiesConverter(messagePropertiesConverter);
        rabbitTemplate.setReceiveTimeout(6000);
        rabbitTemplate.setExchange(DELAY2_EXCHANGE);
        rabbitTemplate.setRoutingKey(DELAY2_ROUTING_KEY);
        return rabbitTemplate;
    }
    @Bean
    public Queue delayA(){
        return new Queue(QUEUE_DELAY2,true);
    }

    @Bean
    public DirectExchange delayExchange(){
        return new DirectExchange(DELAY2_EXCHANGE,true,false);
    }
    @Bean
    public Binding buildBindingDirectA(){
        return BindingBuilder.bind(delayA()).to(delayExchange()).with(DELAY2_ROUTING_KEY);
    }
}
