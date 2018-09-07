package com.my.rabbit.factory;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jim lin
 * 2018/9/5.
 *  如果要监听多个队列，有的队列需要手动ACK，有的不需要手动ACK，此情况下则可以自己定义RabbitListenerContainerFactory
 */
@Service
public class MySimpleRabbitListenerContainerFactory extends SimpleRabbitListenerContainerFactory {
    private AcknowledgeMode acknowledgeMode = AcknowledgeMode.MANUAL;

    @Autowired
    private ConnectionFactory connectionFactory;

    public MySimpleRabbitListenerContainerFactory(){
        super();
    }

    @Override
    protected void initializeContainer(SimpleMessageListenerContainer instance, RabbitListenerEndpoint endpoint) {
        super.initializeContainer(instance, endpoint);
        instance.setConnectionFactory(connectionFactory);
        instance.setAcknowledgeMode(this.acknowledgeMode);
    }
}
