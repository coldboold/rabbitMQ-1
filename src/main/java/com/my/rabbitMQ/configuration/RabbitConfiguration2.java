package com.my.rabbitMQ.configuration;

import org.springframework.amqp.core.*;
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

/**
 * @author jim lin
 *         2018/8/12.
 */
@Configuration
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitConfiguration2 {

    private static final String QUEUE_FANOUT_A = "queue_fanout_A";
    private static final String QUEUE_FANOUT_B = "queue_fanout_B";
    private static final String EXCHANGE_FANOUT = "exchange_fanout";


    private static final String QUEUE_DIRECT_A = "queue_direct_A";
    private static final String DIRECT_EXCHANGE = "direct_exchange";
    private static final String DIRECT_ROUTING_KEY_A = "direct.routing.keyA";

    private static final String QUEUE_TOPIC = "queue_topic";
    private static final String TOPIC_EXCHANGE = "topic_exchange";
    private static final String TOPIC_ROUTING_KEY = "topic.routing.*";

    private static final String QUEUE_HEADERS = "queue_headers";
    private static final String HEADERS_EXCHANGE = "headers_exchange";
    private static final String HEADERS_KEY = "headers.key";



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


    // === 广播模式开始===============
    @Bean(name="fanoutRabbitTemplate")
    public RabbitTemplate getRabbitTemplate()
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(getConnectionFactory());
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setMessageConverter(getMessageConverter());
        rabbitTemplate.setMessagePropertiesConverter(getMessagePropertiesConverter());
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
    // === 广播模式结束===============

    // === direct模式开始===============
    @Bean(name="directRabbitTemplate")
    public RabbitTemplate getDirectRabbitTemplate()
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(getConnectionFactory());
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setMessageConverter(getMessageConverter());
        rabbitTemplate.setMessagePropertiesConverter(getMessagePropertiesConverter());
        rabbitTemplate.setReceiveTimeout(6000);
        //只支持一个默认exchange
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
    // === direct模式结束===============


    // === topic模式开始===============
    @Bean(name="topicRabbitTemplate")
    public RabbitTemplate getTopicRabbitTemplate()
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(getConnectionFactory());
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setMessageConverter(getMessageConverter());
        rabbitTemplate.setMessagePropertiesConverter(getMessagePropertiesConverter());
        rabbitTemplate.setReceiveTimeout(6000);
        //只支持一个默认exchange
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
    // === direct模式结束===============

    // === headers模式开始===============
    @Bean(name="headersRabbitTemplate")
    public RabbitTemplate getHeadersRabbitTemplate()
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(getConnectionFactory());
        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setMessageConverter(getMessageConverter());
        rabbitTemplate.setMessagePropertiesConverter(getMessagePropertiesConverter());
        rabbitTemplate.setReceiveTimeout(6000);
        //只支持一个默认exchange
        rabbitTemplate.setExchange(HEADERS_EXCHANGE);
//        rabbitTemplate.setRoutingKey(TOPIC_ROUTING_KEY);
        return rabbitTemplate;
    }
    @Bean
    public Queue headersA(){
        return new Queue(QUEUE_HEADERS,true);
    }

    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(HEADERS_EXCHANGE,true,false);
    }
    @Bean
    public Binding buildBindingHeadersA(){
        return BindingBuilder.bind(headersA()).to(headersExchange()).where(HEADERS_KEY).matches("123");
    }
    @Bean
    public Binding buildBindingHeadersB(){
        String[] keys = {"key1","key2","key3"};
        return BindingBuilder.bind(headersA()).to(headersExchange()).whereAll(keys).exist();
    }

    @Bean
    public Binding buildBindingHeadersC(){
        String[] keys = {"key4","key5","key6"};
        return BindingBuilder.bind(headersA()).to(headersExchange()).whereAny(keys).exist();
    }
    // === headers模式结束===============


}
