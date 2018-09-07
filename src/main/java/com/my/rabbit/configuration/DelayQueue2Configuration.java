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
 *  spring 自己集成了delay的插件 https://docs.spring.io/spring-amqp/reference/htmlsingle/#delayed-message-exchange
 *  先安装插件：https://dl.bintray.com/rabbitmq/community-plugins/3.7.x/rabbitmq_delayed_message_exchange/rabbitmq_delayed_message_exchange-20171201-3.7.x.zip
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
    @Bean("delay2Queue")
    public Queue delay2Queue(){
        return new Queue(QUEUE_DELAY2,true);
    }

    @Bean("delayExchange")
    public CustomExchange delay2Exchange(){
        Map<String, Object> args = new HashMap<>(16);
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAY2_EXCHANGE,"x-delayed-message",true,false,args);
    }
    @Bean
    public Binding buildBindingDirectA(){
        return BindingBuilder.bind(delay2Queue()).to(delay2Exchange()).with(DELAY2_ROUTING_KEY).noargs();
    }
}
