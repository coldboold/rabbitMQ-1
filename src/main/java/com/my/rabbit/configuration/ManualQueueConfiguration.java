package com.my.rabbit.configuration;

import com.my.rabbit.callback.ManualConfirmCallBack;
import com.my.rabbit.callback.ManualReturnCallBack;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.converter.MessageConverter;
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

    private static final String RISK_SCORE_CARD_IN_QUEUE = "risk_score_card_in";
    private static final String RISK_EXCHANGE = "risk";
    private static final String RISK_SCORE_CARD_IN_KEY = "risk.score.card.in";


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
        rabbitTemplate.setExchange(RISK_EXCHANGE);
        rabbitTemplate.setRoutingKey(RISK_SCORE_CARD_IN_KEY);
        rabbitTemplate.setConfirmCallback(new ManualConfirmCallBack());
        rabbitTemplate.setReturnCallback(new ManualReturnCallBack());
        return rabbitTemplate;
    }
    @Bean
    public Queue directA(){
        return new Queue(RISK_SCORE_CARD_IN_QUEUE,true);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(RISK_EXCHANGE,true,false);
    }
    @Bean
    public Binding buildBindingDirectA(){
        return BindingBuilder.bind(directA()).to(directExchange()).with(RISK_SCORE_CARD_IN_KEY);
    }
}
