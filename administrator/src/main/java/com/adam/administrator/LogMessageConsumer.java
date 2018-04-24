package com.adam.administrator;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

public class LogMessageConsumer extends DefaultConsumer {
    private final Channel channel;
    private final Logger logger = LogManager.getLogger();

    public LogMessageConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        logger.info("Log message: " + message);
        channel.basicAck(envelope.getDeliveryTag(), false);
    }
}
