package com.abc.general_stu;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: AsyncProducer
 * Package: com.abc.general_stu
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/12/3 23:51
 * @Version 1.0
 */
public class AsyncProducer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("pg");
        producer.setNamesrvAddr("rocketmqOS:9876");
        producer.setRetryTimesWhenSendFailed(0);
        producer.setDefaultTopicQueueNums(2);

        producer.start();

        for (int i = 0; i < 100; i++) {
            Message message = new Message("myTopicA", "myTag", ("Hi, " + i).getBytes());
            try {
                producer.send(message, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.println("sendResult = " + sendResult);
                    }

                    @Override
                    public void onException(Throwable e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { throw new RuntimeException(e); }
        producer.shutdown();
    }
}
