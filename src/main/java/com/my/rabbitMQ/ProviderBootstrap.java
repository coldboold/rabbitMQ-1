package com.my.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jim lin
 *         2018/8/12.
 */
@SpringBootApplication(scanBasePackages = "com.my.rabbitMQ")
@EnableRabbit
public class ProviderBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(ProviderBootstrap.class, args);
    }
}
