package com.abc.transaction_stu;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.*;

/**
 * ClassName: TransactionProducer
 * Package: com.abc.transaction_stu
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/12/4 13:17
 * @Version 1.0
 */
public class TransactionProducer {
    public static void main(String[] args) throws MQClientException {
        TransactionMQProducer producer = new TransactionMQProducer("tpg");
        producer.setNamesrvAddr("rocketmqOS:9876");

        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });
        producer.setExecutorService(executorService);
        producer.setTransactionListener(new ICBCTransactionListener());

        producer.start();

        String[] tags = {"TAGA","TAGB","TAGC"};
        for (int i = 0; i < 3; i++) {
            Message message = new Message("TTopic", tags[i], ("Hi, " + i).getBytes());
            TransactionSendResult sendResult = producer.sendMessageInTransaction(message, null);
            System.out.println("发送结果为 = " + sendResult.getSendStatus());
        }
    }
}
