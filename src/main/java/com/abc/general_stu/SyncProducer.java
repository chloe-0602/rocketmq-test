package com.abc.general_stu;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * ClassName: SyncProducer
 * Package: com.abc.general_stu
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/12/3 22:15
 * @Version 1.0
 */
public class SyncProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("pg");
        producer.setNamesrvAddr("rocketmqOS:9876");
        producer.setRetryTimesWhenSendFailed(3);
        producer.setSendMsgTimeout(5000);

        producer.start();
        for (int i = 0; i < 1; i++) {

            Message message = new Message("someTopic", "someTag", ("Hi, " + i).getBytes());
            message.setKeys("key-" + i);
            SendResult sendResult = producer.send(message);
            System.out.println("sendResult = " + sendResult);
        }
        producer.shutdown();
    }
}
