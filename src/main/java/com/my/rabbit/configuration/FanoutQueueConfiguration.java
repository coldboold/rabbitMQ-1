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
public class FanoutQueueConfiguration {

    private static final String QUEUE_FANOUT_A = "queue_fanout_A";
    private static final String QUEUE_FANOUT_B = "queue_fanout_B";
    private static final String EXCHANGE_FANOUT = "exchange_fanout";

    @Bean(name="fanoutRabbitTemplate")
    public RabbitTemplate getHeadersRabbitTemplate(ConnectionFactory connectionFactory,
                                                   MessageConverter messageConverter, MessagePropertiesConverter messagePropertiesConverter)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setMessagePropertiesConverter(messagePropertiesConverter);
        rabbitTemplate.setReceiveTimeout(6000);
        //只支持一个默认exchange
        rabbitTemplate.setExchange(EXCHANGE_FANOUT);
        return rabbitTemplate;
    }

    @Bean
    public Queue fanoutA(){
        return new Queue(QUEUE_FANOUT_A,true);
    }
    @Bean
    public Queue fanoutB(){
        return new Queue(QUEUE_FANOUT_B,true);
    }
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXCHANGE_FANOUT,true,false);
    }
    @Bean
    public Binding buildBindingA(){
        return BindingBuilder.bind(fanoutA()).to(fanoutExchange());
    }
    @Bean
    public Binding buildBindingB(){
        return BindingBuilder.bind(fanoutB()).to(fanoutExchange());
    }

}
