package com.my.rabbit.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author jim lin
 *         2018/8/12.
 */
@Service
@Slf4j
@SuppressWarnings("unused")
public class ManualRabbitClientService {


    @RabbitListener(queues = {"queue_direct_B"}
    ,containerFactory = "mySimpleRabbitListenerContainerFactory"
    )
    public void receive(Message message, Channel channel){
        log.info("================queue_direct_B================:{}",new String(message.getBody()));
        try {
            //消息确认，第一个参数为，消息的唯一ID，第二个参数表示是否批量确认，即确认所有小于当前消息ID。
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            //丢弃这条休息
            /*try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }*/
        }
    }
}
