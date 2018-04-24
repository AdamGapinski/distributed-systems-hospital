package com.adam.common;

public enum TestType {
    KNEE(Properties.TEST_ORDER_KNEE_QUEUENAME, Properties.TEST_ORDER_KNEE_TOPIC),
    ELBOW(Properties.TEST_ORDER_ELBOW_QUEUENAME, Properties.TEST_ORDER_ELBOW_TOPIC),
    HIP(Properties.TEST_ORDER_HIP_QUEUENAME, Properties.TEST_ORDER_HIP_TOPIC);

    private final String queueName;
    private final String routingKey;

    TestType(String queueName, String routingKey) {
        this.queueName = queueName;
        this.routingKey = routingKey;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}
