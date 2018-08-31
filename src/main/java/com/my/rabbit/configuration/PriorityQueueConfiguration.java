package com.my.rabbit.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jim lin
 * 2018/8/30.
 */
@Configuration
@Import(RabbitConfiguration.class)
public class PriorityQueueConfiguration {


    private static final String PRIORITY_QUEUE = "priority_queue";
    private static final String PRIORITY_EXCHANGE = "priority_exchange";
    private static final String PRIORITY_KEY = "priority.key";

    @Bean(name="priorityRabbitTemplate")
    public RabbitTemplate getHeadersRabbitTemplate(ConnectionFactory connectionFactory,
                                                   MessageConverter messageConverter, MessagePropertiesConverter messagePropertiesConverter)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setMessagePropertiesConverter(messagePropertiesConverter);
        rabbitTemplate.setReceiveTimeout(6000);
        rabbitTemplate.setExchange(PRIORITY_EXCHANGE);
        rabbitTemplate.setRoutingKey(PRIORITY_KEY);
        return rabbitTemplate;
    }

    @Bean
    public Queue priorityQueue(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-priority",99);
        Queue queue = new Queue(PRIORITY_QUEUE,true,false,false,arguments);
        return queue;
    }

    @Bean("priorityDirectExchange")
    public DirectExchange directExchange(){
        return  new DirectExchange(PRIORITY_EXCHANGE,true,false);
    }

    @Bean
    public Binding buildBinding(){
        return  BindingBuilder.bind(priorityQueue()).to(directExchange()).with(PRIORITY_KEY);
    }


}
