package com.my.rabbit.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author jim lin
 *         2018/8/12.
 */
@Service
public class RabbitSendService {

    @Autowired
    @Qualifier("fanoutRabbitTemplate")
    private AmqpTemplate fanoutRabbitTemplate;

    @Autowired
    @Qualifier("directRabbitTemplate")
    private AmqpTemplate directRabbitTemplate;

    @Autowired
    @Qualifier("topicRabbitTemplate")
    private AmqpTemplate topicRabbitTemplate;
    @Autowired
    @Qualifier("headersRabbitTemplate")
    private AmqpTemplate headersRabbitTemplate;

    public void fanoutSendMessage(int i){
        fanoutRabbitTemplate.convertAndSend("fanoutSendMessage hello rabbit===="+i);
    }

    public void directSendMessage(int i){
        directRabbitTemplate.convertAndSend("directSendMessage hello rabbit===="+i);
    }

    public void topicSendMessage(int i){
        topicRabbitTemplate.convertAndSend("topic.routing.a","topicSendMessage hello rabbit===="+i);
    }

    public void topicSendMessage2(int i){
        topicRabbitTemplate.convertAndSend("topic.routing.a","topicSendMessage2 hello rabbit===="+i);
    }
    public void topicSendMessage3(int i){
        //不会被消费掉，因为找不到匹配的Queue
        topicRabbitTemplate.convertAndSend("topic.routing.a.x","topicSendMessage3 hello rabbit===="+i);
    }

    /**
     * 单条key和value都匹配则发送到queue中
     */
    public void headersSendMessage(int i){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("headers.key","123");
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        String messageStr = "headersMessage hello rabbit "+i;
        Message message = new Message(messageStr.getBytes(),messageProperties);
        headersRabbitTemplate.send(message);
    }

    /**
     * 全部匹配key才会发送到queue中
     */
    public void headersSendMessage2(int i){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("key1","123");
        messageProperties.setHeader("key2","123");
        messageProperties.setHeader("key3","123");
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        String messageStr = "headersSendMessage2 hello rabbit "+i;
        Message message = new Message(messageStr.getBytes(),messageProperties);
        headersRabbitTemplate.send(message);
    }

    /**
     * 单个key匹配，不是全部匹配不发送到queue中
     */
    public void headersSendMessage3(int i){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("key1","123");
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        String messageStr = "headersSendMessage3 hello rabbit "+i;
        Message message = new Message(messageStr.getBytes(),messageProperties);
        headersRabbitTemplate.send(message);
    }

    /**
     * 只要有一个key匹配则发送到队列中
     */
    public void headersSendMessage4(int i){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("key4","123");
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        String messageStr = "headersSendMessage4 hello rabbit "+i;
        Message message = new Message(messageStr.getBytes(),messageProperties);
        headersRabbitTemplate.send(message);
    }
}
