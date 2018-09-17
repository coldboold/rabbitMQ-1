package com.my.rabbit.util;

import com.rabbitmq.client.Channel;

/**
 * @author jim lin
 * 2018/9/17.
 */
public class RabbitMQUtil {

    private static Channel CHANNEL ;

    public static Channel getChannel(){
        return CHANNEL;
    }

    public static void setChannel(Channel channel){
        CHANNEL = channel;
    }
}
