package com.my.rabbit.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
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
public class Delay2RabbitSendService {

    @Autowired
    @Qualifier("delay2RabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    public void send(int i){
        String messageStr = "hello delay rabbit "+i;
//        rabbitTemplate.convertAndSend("delay2_exchange","delay2.routing.key",messageStr, new MessagePostProcessor() {
//            @Override
//            public Message postProcessMessage(Message message) throws AmqpException {
//                message.getMessageProperties().setDelay(10000);
//                return message;
//            }
//        });
        rabbitTemplate.convertAndSend("delay2_exchange","delay2.routing.key",messageStr, (Message message) ->{
            message.getMessageProperties().setDelay(10000);
            return message;
        });
    }

}
