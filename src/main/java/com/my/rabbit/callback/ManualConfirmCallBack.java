package com.my.rabbit.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * @author jim lin
 * 2018/9/4.
 */
@Slf4j
public class ManualConfirmCallBack implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("CorrelationData:{},ack:{},cause:{}",correlationData,ack,cause);
    }
}
