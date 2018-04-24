package com.adam.common;

public class Properties {
    public static final String TOPIC_EXCHANGE_NAME =        "hospital.orthopaedic.topic-exchange";

    public static final String ADMINISTRATOR_LOG_TOPIC =    "hospital.orthopaedic.*.*";
    public static final String ADMINISTRATOR_MSG_TOPIC =    "hospital.orthopaedic.administrator-msg";

    public static final String TEST_ORDER_KNEE_TOPIC =      "hospital.orthopaedic.test-orders.knee";
    public static final String TEST_ORDER_ELBOW_TOPIC =     "hospital.orthopaedic.test-orders.elbow";
    public static final String TEST_ORDER_HIP_TOPIC =       "hospital.orthopaedic.test-orders.hip";

    public static final String TEST_ORDER_KNEE_QUEUENAME =  "hospital.orthopaedic.test-orders.knee";
    public static final String TEST_ORDER_ELBOW_QUEUENAME = "hospital.orthopaedic.test-orders.elbow";
    public static final String TEST_ORDER_HIP_QUEUENAME =   "hospital.orthopaedic.test-orders.hip";

    public static final String ORTHOPEDIST_TOPIC_BASE =     "hospital.orthopaedic.doctor.";
}
