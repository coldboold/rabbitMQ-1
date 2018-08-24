package com.my.rabbitMQ;

import com.my.rabbitMQ.service.RabbitSendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jim lin
 *         2018/8/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProviderBootstrap.class)
public class BaseTest {

    @Autowired
    private RabbitSendService rabbitService;

    @Test
    public void send(){
        for (int i=0;i<100;i++){
            rabbitService.fanoutSendMessage(i);
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
