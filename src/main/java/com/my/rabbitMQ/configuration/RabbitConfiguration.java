/*
package com.my.rabbitMQ.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

;

*/
/**
 * @author jim lin
 *         2018/8/12.
 *//*

@Configuration
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitConfiguration {

    @Autowired
    private RabbitProperties rabbitProperties;

    @Bean("connectionFactory")
    public ConnectionFactory getConnectionFactory() {
        com.rabbitmq.client.ConnectionFactory rabbitConnectionFactory =
                new com.rabbitmq.client.ConnectionFactory();
        rabbitConnectionFactory.setHost(rabbitProperties.getHost());
        rabbitConnectionFactory.setPort(rabbitProperties.getPort());
        rabbitConnectionFactory.setUsername(rabbitProperties.getUsername());
        rabbitConnectionFactory.setPassword(rabbitProperties.getPassword());
        rabbitConnectionFactory.setVirtualHost(rabbitProperties.getVirtualHost());

        ConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitConnectionFactory);
        return connectionFactory;
    }

    @Bean(name="rabbitAdmin")
    public RabbitAdmin getRabbitAdmin()
    {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(getConnectionFactory());
        rabbitAdmin.setAutoStartup(true);
        Queue sendQueue = new Queue("queue1",true,false,false);
        rabbitAdmin.declareQueue(sendQueue);
        DirectExchange sendExchange = new DirectExchange("exchange1",true,false);
        rabbitAdmin.declareExchange(sendExchange);
        Binding sendMessageBinding =
                new Binding("queue1", Binding.DestinationType.QUEUE,
                        "exchange1","key1", null);
        rabbitAdmin.declareBinding(sendMessageBinding);
        return rabbitAdmin;
    }

    @Bean(name="serializerMessageConverter")
    public MessageConverter getMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean(name="messagePropertiesConverter")
    public MessagePropertiesConverter getMessagePropertiesConverter()
    {
        return new DefaultMessagePropertiesConverter();
    }

    @Bean(name="rabbitTemplate")
    public RabbitTemplate getRabbitTemplate()
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(getConnectionFactory());
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setMessageConverter(getMessageConverter());
        rabbitTemplate.setMessagePropertiesConverter(getMessagePropertiesConverter());
//        rabbitTemplate.setReplyAddress("my.retry.queue");
        rabbitTemplate.setReceiveTimeout(6000);
        rabbitTemplate.setQueue("queue1");
        rabbitTemplate.setExchange("exchange1");
        rabbitTemplate.setRoutingKey("key1");
        return rabbitTemplate;
    }
//
//    @Bean(name="springMessageQueue")
//    public Queue createQueue(@Qualifier("rabbitAdmin")RabbitAdmin rabbitAdmin)
//    {
//        Queue sendQueue = new Queue("my.queue",true,false,false);
//        rabbitAdmin.declareQueue(sendQueue);
//        return sendQueue;
//    }
//
//    @Bean(name="springMessageExchange")
//    public Exchange createExchange(@Qualifier("rabbitAdmin")RabbitAdmin rabbitAdmin)
//    {
//        DirectExchange sendExchange = new DirectExchange("my.exchange",true,false);
//        rabbitAdmin.declareExchange(sendExchange);
//        return sendExchange;
//    }
//
//    @Bean(name="springMessageBinding")
//    public Binding createMessageBinding(@Qualifier("rabbitAdmin")RabbitAdmin rabbitAdmin)
//    {
//        Map<String,Object> arguments = new HashMap<String,Object>();
//        Binding sendMessageBinding =
//                new Binding("my.queue", Binding.DestinationType.QUEUE,
//                        "my.exchange","my.messge.key", arguments);
//        rabbitAdmin.declareBinding(sendMessageBinding);
//        return sendMessageBinding;
//    }
//
//    @Bean(name="springReplyMessageQueue")
//    public Queue createReplyQueue(@Qualifier("rabbitAdmin")RabbitAdmin rabbitAdmin)
//    {
//        Queue replyQueue = new Queue("my.retry.queue",true,false,false);
//        rabbitAdmin.declareQueue(replyQueue);
//        return replyQueue;
//    }
//
//    @Bean(name="springReplyMessageExchange")
//    public Exchange createReplyExchange(@Qualifier("rabbitAdmin")RabbitAdmin rabbitAdmin)
//    {
//        DirectExchange replyExchange = new DirectExchange("my.retry.exchange",true,false);
//        rabbitAdmin.declareExchange(replyExchange);
//        return replyExchange;
//    }
//
//    @Bean(name="springReplyMessageBinding")
//    public Binding createReplyMessageBinding(@Qualifier("rabbitAdmin")RabbitAdmin rabbitAdmin)
//    {
//        Map<String,Object> arguments = new HashMap<>(16);
//        Binding replyMessageBinding =
//                new Binding("my.retry.queue", Binding.DestinationType.QUEUE,
//                        "my.retry.exchange", "my.retry.key", arguments);
//        rabbitAdmin.declareBinding(replyMessageBinding);
//        return replyMessageBinding;
//    }
}
*/
