package com.adam.orthopedist;

import com.adam.common.*;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

class Orthopedist {
    private final String name;
    private final String host;
    private Channel channel;
    private final Logger logger = LogManager.getLogger();

    private String setupTestResultsQueue() throws IOException {
        String formattedName = name.trim().toLowerCase().replace(" ", "-");
        String bindKey = Properties.ORTHOPEDIST_TOPIC_BASE + formattedName;
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, Properties.TOPIC_EXCHANGE_NAME, bindKey);
        logger.debug("Created queue: " + queueName + ", bound with " + bindKey);
        return queueName;
    }

    Orthopedist(String host, String name) {
        this.host = host;
        this.name = name;
    }

    void start() throws IOException, TimeoutException {
        Connection connection = Utils.setupConnection(host);
        channel = connection.createChannel();
        Utils.setupTestOrderQueues(channel);
        String resultsQueueName = setupTestResultsQueue();
        String msgQueue = Utils.setupQueueForAdminMessages(channel);
        logger.info("Orthopedist " + name);
        logger.info("Waiting for messages...");
        channel.basicConsume(msgQueue, false, new AdministratorMessageConsumer(channel));
        channel.basicConsume(resultsQueueName, false, new TestResultConsumer(channel));
        startOrderingTests();
        logger.info("Closing...");
        channel.close();
        connection.close();
    }

    private void startOrderingTests() throws IOException, TimeoutException {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Press enter to order new test or type exit");
            String command = scanner.nextLine();
            if (command.trim().toLowerCase().equals("exit")) {
                break;
            }
            System.out.println("Ordering new test");
            System.out.println("Test type (knee, elbow or hip): ");
            String testType = scanner.nextLine();
            Test test;
            try {
                test = new Test(TestType.valueOf(testType.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid test type. Proper test types are knee, elbow or hip");
                continue;
            }
            System.out.println("Patient name: ");
            String patientName = scanner.nextLine();
            TestOrder order = new TestOrder(test, name, patientName);
            orderTest(order);
        }
    }

    private void orderTest(TestOrder order) throws IOException {
        Gson gson = new Gson();
        String orderJson = gson.toJson(order);
        byte[] orderBytes = orderJson.getBytes();
        String routingKey = order.getTest().getType().getRoutingKey();
        channel.basicPublish(Properties.TOPIC_EXCHANGE_NAME, routingKey, null, orderBytes);
    }
}
