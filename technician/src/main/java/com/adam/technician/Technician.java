package com.adam.technician;

import com.adam.common.AdministratorMessageConsumer;
import com.adam.common.TestType;
import com.adam.common.Utils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

class Technician {
    private String host;
    private Channel channel;
    private List<TestType> testTypes = new ArrayList<>();
    private final Logger logger = LogManager.getLogger();

    Technician(String host, String testType1, String testType2) throws Exception {
        setTestTypes(testType1, testType2);
        this.host = host;
    }

    void start() throws IOException, TimeoutException {
        Connection connection = Utils.setupConnection(host);
        channel = connection.createChannel();
        Utils.setupTestOrderQueues(channel);
        String msgQueue = Utils.setupQueueForAdminMessages(channel);
        String testTypesString = getFormattedTestTypes();
        logger.info("Technician " + testTypesString);
        logger.info("Waiting for messages...");
        channel.basicQos(1);
        channel.basicConsume(msgQueue, false, new AdministratorMessageConsumer(channel));
        for (TestType testType : testTypes) {
            channel.basicConsume(testType.getQueueName(), false, new TestOrderConsumer(channel));
        }
        setCloseOnExit(connection);
    }

    private String getFormattedTestTypes() {
        StringBuilder testTypesString = new StringBuilder();
        for (TestType testType : testTypes) {
            if (!testTypesString.toString().isEmpty()) {
                testTypesString.append(", ");
            }
            testTypesString.append(testType.toString().toLowerCase());
        }
        return testTypesString.toString();
    }

    private void setTestTypes(String testType1, String testType2) {
        try {
            TestType first = TestType.valueOf(testType1.toUpperCase());
            TestType second = TestType.valueOf(testType2.toUpperCase());
            if (first == second) {
                throw new IllegalArgumentException("Invalid test types. Test types should be different");
            }
            testTypes.add(first);
            testTypes.add(second);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid test type. Proper test types are knee, elbow or hip", e);
        }
    }

    private void setCloseOnExit(Connection connection) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                channel.close();
                connection.close();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }));
    }
}
