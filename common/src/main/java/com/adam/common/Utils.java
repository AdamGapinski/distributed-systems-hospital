package com.adam.common;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Utils {

    private static final Logger logger = LogManager.getLogger();

    public static void setupTestOrderQueues(Channel channel) throws IOException {
        channel.exchangeDeclare(Properties.TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        channel.queueDeclare(Properties.TEST_ORDER_KNEE_QUEUENAME, false, false, false, null);
        channel.queueDeclare(Properties.TEST_ORDER_ELBOW_QUEUENAME, false, false, false, null);
        channel.queueDeclare(Properties.TEST_ORDER_HIP_QUEUENAME, false, false, false, null);

        channel.queueBind(Properties.TEST_ORDER_KNEE_QUEUENAME, Properties.TOPIC_EXCHANGE_NAME, Properties.TEST_ORDER_KNEE_TOPIC);
        logger.debug("Declared queue: " + Properties.TEST_ORDER_KNEE_QUEUENAME + ", bound with " + Properties.TEST_ORDER_KNEE_TOPIC);
        channel.queueBind(Properties.TEST_ORDER_ELBOW_QUEUENAME, Properties.TOPIC_EXCHANGE_NAME, Properties.TEST_ORDER_ELBOW_TOPIC);
        logger.debug("Declared queue: " + Properties.TEST_ORDER_ELBOW_QUEUENAME + ", bound with " + Properties.TEST_ORDER_ELBOW_TOPIC);
        channel.queueBind(Properties.TEST_ORDER_HIP_QUEUENAME, Properties.TOPIC_EXCHANGE_NAME, Properties.TEST_ORDER_HIP_TOPIC);
        logger.debug("Declared queue: " + Properties.TEST_ORDER_HIP_QUEUENAME + ", bound with " + Properties.TEST_ORDER_HIP_TOPIC);
    }

    public static Connection setupConnection(String host) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        return factory.newConnection();
    }

    public static String setupQueueForAdminMessages(Channel channel) throws IOException {
        String msgQueue = channel.queueDeclare().getQueue();
        channel.queueBind(msgQueue, Properties.TOPIC_EXCHANGE_NAME, Properties.ADMINISTRATOR_MSG_TOPIC);
        logger.debug("Created queue: " + msgQueue + ", bound with " + Properties.ADMINISTRATOR_MSG_TOPIC);
        return msgQueue;
    }
}
