package com.uaes.esw.gwmc30demo.constant;

public class InfraKafkaConstants {
    public final static String KAFKA_CONFIG_BOOTSTRAP_SERVERS_CONFIG =
            "emr-worker-1.cluster-47779:9092,emr-worker-2.cluster-47779:9092";
    public final static String KAFKA_CONFIG_GROUP_ID_CONFIG = "gwm-c30-001";
    public final static String KAFKA_CONFIG_ENABLE_AUTO_COMMIT_CONFIG = "true";
    public final static String KAFKA_CONFIG_AUTO_COMMIT_INTERVAL_MS_CONFIG = "1000";
    public final static String KAFKA_CONFIG_SESSION_TIMEOUT_MS_CONFIG = "30000";
    public final static String KAFKA_CONFIG_SERIALIZER_CLASS_CONFIG =
            "org.apache.kafka.common.serialization.StringSerializer";
    public final static String KAFKA_CONFIG_DESERIALIZER_CLASS_CONFIG =
            "org.apache.kafka.common.serialization.StringDeserializer";
    public final static String KAFKA_CONFIG_AUTO_OFFSET_RESET_CONFIG = "earliest";
    public final static String KAFKA_CONFIG_CURRENT_DM_KEY = "1";
    public final static String KAFKA_CONFIG_DEFAULT_DM_KEY = "2";
    public final static String KAFKA_CONFIG_NORMAL_DM_KEY = "3";
    public final static String KAFKA_CONFIG_CLIENT_ID = "clientID-c30-1";
    public final static String KAFKA_CONFIG_TOPIC = "gwm-c30-01";
}
