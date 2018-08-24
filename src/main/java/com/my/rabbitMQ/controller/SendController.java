package com.my.rabbitMQ.controller;

import com.my.rabbitMQ.service.RabbitSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jim lin
 *         2018/8/20.
 */
@RestController
public class SendController {
    @Autowired
    private RabbitSendService rabbitService;

    @RequestMapping("sendFanout")
    public String sendFanout(@RequestParam("count") int count){
        for (int i=0;i<count;i++){
            rabbitService.fanoutSendMessage(i);
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "00000000";

    }
    @RequestMapping("sendDirect")
    public String sendDirect(@RequestParam("count") int count){
        for (int i=0;i<count;i++){
            rabbitService.directSendMessage(i);
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "00000000";
    }
    @RequestMapping("sendTopic")
    public String sendTopic(@RequestParam("count") int count){
        for (int i=0;i<count;i++){
            rabbitService.topicSendMessage(i);
            rabbitService.topicSendMessage2(i);
            rabbitService.topicSendMessage3(i);
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "00000000";
    }
    @RequestMapping("sendHeaders")
    public String sendHeaders(@RequestParam("count") int count){
        for (int i=0;i<count;i++){
            rabbitService.headersSendMessage(i);
            rabbitService.headersSendMessage2(i);
            rabbitService.headersSendMessage3(i);
            rabbitService.headersSendMessage4(i);
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "00000000";
    }
}
