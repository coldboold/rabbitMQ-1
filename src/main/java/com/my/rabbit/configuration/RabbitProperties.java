package com.my.rabbit.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jim lin
 *         2018/8/12.
 */
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Data
public class RabbitProperties {

    private String host;
    private int port;
    private String username;
    private String password;
    private String virtualHost;
}
