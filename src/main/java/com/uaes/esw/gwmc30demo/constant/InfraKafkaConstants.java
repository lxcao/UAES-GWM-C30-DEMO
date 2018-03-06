package com.uaes.esw.gwmc30demo.constant;

public class InfraKafkaConstants {
    public final static String KAFKA_CONFIG_BOOTSTRAP_SERVERS_CONFIG =
            "emr-worker-1.cluster-47779:9092,emr-worker-2.cluster-47779:9092";
    public final static String KAFKA_CONFIG_GROUP_ID_CONFIG = "uaes-group-002";
    public final static String KAFKA_CONFIG_ENABLE_AUTO_COMMIT_CONFIG = "true";
    public final static String KAFKA_CONFIG_AUTO_COMMIT_INTERVAL_MS_CONFIG = "1000";
    public final static String KAFKA_CONFIG_SESSION_TIMEOUT_MS_CONFIG = "30000";
    public final static String KAFKA_CONFIG_KEY_DESERIALIZER_CLASS_CONFIG =
            "org.apache.kafka.common.serialization.IntegerDeserializer";
    public final static String KAFKA_CONFIG_VALUE_DESERIALIZER_CLASS_CONFIG =
            "org.apache.kafka.common.serialization.StringDeserializer";
    public final static String KAFKA_CONFIG_AUTO_OFFSET_RESET_CONFIG = "earliest";
}
