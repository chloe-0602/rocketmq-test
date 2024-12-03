package com.abc.general_stu;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * ClassName: OnewayProducer
 * Package: com.abc.general_stu
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/12/3 23:57
 * @Version 1.0
 */
public class OnewayProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("pgg");
        producer.setNamesrvAddr("rocketmqOS:9876");

        producer.start();
        for (int i = 0; i < 100; i++) {
            producer.sendOneway(new Message("single", "someTag", ("Hi, ").getBytes()));
        }

        producer.shutdown();
        System.out.println("producer = " + producer);
    }
}
