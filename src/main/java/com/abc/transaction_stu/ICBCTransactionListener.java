package com.abc.transaction_stu;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * ClassName: ICBCTransactionListener
 * Package: com.abc.transaction_stu
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/12/4 13:24
 * @Version 1.0
 */
public class ICBCTransactionListener implements TransactionListener {
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        if(StringUtils.equals("TAGA", msg.getTags())){
            return LocalTransactionState.COMMIT_MESSAGE;
        }else if (StringUtils.equals("TAGB", msg.getTags())){
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }else if (StringUtils.equals("TAGC", msg.getTags())){
            return LocalTransactionState.UNKNOW;
        }
        return LocalTransactionState.UNKNOW;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        System.out.println("执行方法回查： " + msg.getTags());
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
