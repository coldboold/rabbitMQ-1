package com.my.rabbit.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author jim lin
 * 2018/8/30.
 */
@Service
public class DelayRabbitSendService {

    @Autowired
    @Qualifier("delayRabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    public void send(int i){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
//        messageProperties.setDelay(6);
        String messageStr = "hello delay rabbit "+i;
        Message message = new Message(messageStr.getBytes(),messageProperties);
        rabbitTemplate.send("ttl_exchange","ttl.key",message);
    }

}
