package com.my.rabbit.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author jim lin
 *         2018/8/12.
 */
@Service
@Slf4j
@SuppressWarnings("unused")
public class PriorityRabbitClientService {


    @RabbitListener(queues = {"priority_queue"})
    public void receive(Message message){
        log.info("================priority_queue================:{}",new String(message.getBody()));
    }
}
