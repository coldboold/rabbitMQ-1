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
public class DelayQueueConfiguration {


    private static final String TTL_QUEUE = "ttl_queue";
    private static final String TTL_EXCHANGE = "ttl_exchange";
    private static final String TTL_KEY = "ttl.key";

    private static final String DLX_EXCHANGE = "dlx_exchange";

    private static final String DELAY_QUEUE = "delay_queue";

    @Bean(name="delayRabbitTemplate")
    public RabbitTemplate getHeadersRabbitTemplate(ConnectionFactory connectionFactory,
                                                   MessageConverter messageConverter, MessagePropertiesConverter messagePropertiesConverter)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setMessagePropertiesConverter(messagePropertiesConverter);
        rabbitTemplate.setReceiveTimeout(6000);
        rabbitTemplate.setExchange(TTL_EXCHANGE);
        rabbitTemplate.setRoutingKey(TTL_KEY);
        return rabbitTemplate;
    }

    /**
     * 过期队列，数据会转到死信队列中
     * x-message-ttl 过期时间
     */
    @Bean
    public Queue ttlQueue(){
        return QueueBuilder.durable(TTL_QUEUE)
                .withArgument("x-dead-letter-exchange",DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key",TTL_KEY)
                .withArgument("x-message-ttl", 10000)
                .build();
    }

    /**
     * 死信交换器对应的队列，消费者只要监控这个队列就好。
     */
    @Bean
    public Queue delayQueue(){
        return QueueBuilder.durable(DELAY_QUEUE)
                .build();
    }

    @Bean("ttlDirectExchange")
    public DirectExchange ttlDirectExchange(){
        return  new DirectExchange(TTL_EXCHANGE,true,false);
    }

    @Bean("dlxDirectExchange")
    public DirectExchange dlxDirectExchange(){
        return  new DirectExchange(DLX_EXCHANGE,true,false);
    }

    @Bean("ttlBinding")
    public Binding buildBinding(){
        return  BindingBuilder.bind(ttlQueue()).to(ttlDirectExchange()).with(TTL_KEY);
    }
    @Bean("dlxBinding")
    public Binding buildBinding2(){
        return  BindingBuilder.bind(delayQueue()).to(dlxDirectExchange()).with(TTL_KEY);
    }


}
