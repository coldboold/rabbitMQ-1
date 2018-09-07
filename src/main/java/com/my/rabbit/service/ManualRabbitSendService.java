package com.my.rabbit.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author jim lin
 * 2018/8/30.
 */
@Service
public class ManualRabbitSendService {

    @Autowired
    @Qualifier("manualRabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    public void send(int i){
        String messageStr = "hello manual rabbit "+i;
//        CorrelationData correlationData = new CorrelationData(i +"");
        rabbitTemplate.convertAndSend(messageStr);
    }

}
