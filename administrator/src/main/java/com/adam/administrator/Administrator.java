package com.adam.administrator;

import com.adam.common.AdministratorMessage;
import com.adam.common.Properties;
import com.adam.common.Utils;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

class Administrator {
    private final String host;
    private Channel channel;
    private final Logger logger = LogManager.getLogger();

    Administrator(String host) throws Exception {
        this.host = host;
    }

    void start() throws IOException, TimeoutException {
        Connection connection = Utils.setupConnection(host);
        channel = connection.createChannel();
        Utils.setupTestOrderQueues(channel);
        String logQueueName = setupLogQueue();
        logger.info("Administrator");
        logger.info("Waiting for messages...");
        channel.basicConsume(logQueueName, false, new LogMessageConsumer(channel));
        startSendingMessages();
        logger.info("Closing...");
        channel.close();
        connection.close();
    }

    private  String setupLogQueue() throws IOException {
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, Properties.TOPIC_EXCHANGE_NAME, Properties.ADMINISTRATOR_LOG_TOPIC);
        logger.debug("Created queue: " + queueName + ", bound with " + Properties.ADMINISTRATOR_LOG_TOPIC);
        return queueName;
    }

    private  void startSendingMessages() throws IOException, TimeoutException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Press enter to send message or type exit");
            String command = scanner.nextLine();
            if (command.trim().toLowerCase().equals("exit")) {
                break;
            }
            System.out.println("Message: ");
            String messageString = scanner.nextLine();
            sendMessage(messageString);
        }
    }

    private  void sendMessage(String message) throws IOException {
        AdministratorMessage administratorMessage = new AdministratorMessage(message);
        Gson gson = new Gson();
        String messageJson = gson.toJson(administratorMessage);
        byte[] messageBytes = messageJson.getBytes();
        String routingKey = Properties.ADMINISTRATOR_MSG_TOPIC;
        channel.basicPublish(Properties.TOPIC_EXCHANGE_NAME, routingKey, null, messageBytes);
    }
}
