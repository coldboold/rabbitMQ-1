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
public class TopickQueueConfiguration {


    private static final String QUEUE_TOPIC = "queue_topic";
    private static final String TOPIC_EXCHANGE = "topic_exchange";
    private static final String TOPIC_ROUTING_KEY = "topic.routing.*";

    @Bean(name="topicRabbitTemplate")
    public RabbitTemplate getHeadersRabbitTemplate(ConnectionFactory connectionFactory,
                                                   MessageConverter messageConverter, MessagePropertiesConverter messagePropertiesConverter)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setMessagePropertiesConverter(messagePropertiesConverter);
        rabbitTemplate.setReceiveTimeout(6000);
        rabbitTemplate.setExchange(TOPIC_EXCHANGE);
        rabbitTemplate.setRoutingKey(TOPIC_ROUTING_KEY);
        return rabbitTemplate;
    }

    @Bean
    public Queue topicA(){
        return new Queue(QUEUE_TOPIC,true);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE,true,false);
    }
    @Bean
    public Binding buildBindingTopicA(){
        return BindingBuilder.bind(topicA()).to(topicExchange()).with(TOPIC_ROUTING_KEY);
    }

}
