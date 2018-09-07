package com.my.rabbit.configuration;

import com.my.rabbit.callback.ManualConfirmCallBack;
import com.my.rabbit.callback.ManualReturnCallBack;
import com.my.rabbit.listener.ManualRabbitClientService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.ListenerContainerFactoryBean;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * @author jim lin
 * 2018/8/30.
 */
@Configuration
@Import(RabbitConfiguration.class)
@EnableConfigurationProperties(RabbitProperties.class)
public class ManualQueueConfiguration {

    private static final String QUEUE_DIRECT_B = "queue_direct_B";
    private static final String DIRECT_EXCHANGE_B = "direct_exchange_B";
    private static final String DIRECT_ROUTING_KEY_B = "direct.routing.keyB";


    @Bean(name="manualRabbitTemplate")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate getHeadersRabbitTemplate(ConnectionFactory connectionFactory,
                                                   MessageConverter messageConverter, MessagePropertiesConverter messagePropertiesConverter)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setMessagePropertiesConverter(messagePropertiesConverter);
        rabbitTemplate.setReceiveTimeout(6000);
        rabbitTemplate.setExchange(DIRECT_EXCHANGE_B);
        rabbitTemplate.setRoutingKey(DIRECT_ROUTING_KEY_B);
        rabbitTemplate.setConfirmCallback(new ManualConfirmCallBack());
        rabbitTemplate.setReturnCallback(new ManualReturnCallBack());
        return rabbitTemplate;
    }
    @Bean
    public Queue directA(){
        return new Queue(QUEUE_DIRECT_B,true);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(DIRECT_EXCHANGE_B,true,false);
    }
    @Bean
    public Binding buildBindingDirectA(){
        return BindingBuilder.bind(directA()).to(directExchange()).with(DIRECT_ROUTING_KEY_B);
    }
}
