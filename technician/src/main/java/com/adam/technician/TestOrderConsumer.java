package com.adam.technician;

import com.adam.common.Properties;
import com.adam.common.Test;
import com.adam.common.TestOrder;
import com.adam.common.TestResult;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

public class TestOrderConsumer extends DefaultConsumer {
    private final Channel channel;
    private final Logger logger = LogManager.getLogger();

    public TestOrderConsumer(Channel channel) {
        super(channel);

        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        logger.debug(Thread.currentThread().getName());
        Gson gson = new Gson();
        TestOrder testOrder = gson.fromJson(message, TestOrder.class);
        logger.info("Received: " + testOrder);
        TestResult testResult = new TestResult(new Test(testOrder.getTest().getType(), true), testOrder.getPatientName());
        sendResult(testResult, testOrder.getDoctorName());
        channel.basicAck(envelope.getDeliveryTag(), false);
    }

    private void sendResult(TestResult testResult, String doctorName) throws IOException {
        Gson gson = new Gson();
        String orderJson = gson.toJson(testResult);
        byte[] orderBytes = orderJson.getBytes();
        String routingKey = Properties.ORTHOPEDIST_TOPIC_BASE + doctorName.trim().toLowerCase().replace(" ", "-");
        channel.basicPublish(Properties.TOPIC_EXCHANGE_NAME, routingKey, null, orderBytes);
    }
}
