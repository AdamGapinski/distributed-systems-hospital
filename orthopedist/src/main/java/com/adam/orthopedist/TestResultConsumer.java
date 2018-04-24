package com.adam.orthopedist;

import com.adam.common.TestResult;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

public class TestResultConsumer extends DefaultConsumer {
    private final Channel channel;
    private final Logger logger = LogManager.getLogger();

    public TestResultConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String messageJson = new String(body, "UTF-8");
        Gson gson = new Gson();
        TestResult message = gson.fromJson(messageJson, TestResult.class);
        logger.info("Received: " + message);
        channel.basicAck(envelope.getDeliveryTag(), false);
    }
}
