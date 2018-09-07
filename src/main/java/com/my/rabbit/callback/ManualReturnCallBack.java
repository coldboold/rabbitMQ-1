package com.my.rabbit.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author jim lin
 * 2018/9/4.
 */
@Slf4j
public class ManualReturnCallBack implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("===============returnedMessage==========:{}"+message.getMessageProperties().getCorrelationId());
    }
}
