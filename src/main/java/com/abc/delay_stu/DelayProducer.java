package com.abc.delay_stu;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.net.SocketImpl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * ClassName: DelayProducer
 * Package: com.abc.delay_stu
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/12/4 10:27
 * @Version 1.0
 */
public class DelayProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("pg");
        producer.setNamesrvAddr("rocketmqOS:9876");

        producer.start();

        for (int i = 0; i < 1; i++) {
            Message message = new Message("TopicB", "someTag", ("Hi, " + i).getBytes());
            message.setDelayTimeLevel(3); // 10s
            SendResult sendResult = producer.send(message);
            System.out.println( new SimpleDateFormat("mm:ss").format(new Date()));
            System.out.println(sendResult);
        }

        producer.shutdown();
    }
}
