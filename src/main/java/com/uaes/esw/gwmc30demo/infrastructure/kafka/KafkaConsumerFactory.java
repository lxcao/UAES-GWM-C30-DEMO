package com.uaes.esw.gwmc30demo.infrastructure.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.uaes.esw.gwmc30demo.constant.InfraKafkaConstants.*;

public class KafkaConsumerFactory {


    private final KafkaConsumer<Integer, String> kafkaConsumer;
    private final String topic;

    public KafkaConsumerFactory(String topic) {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONFIG_BOOTSTRAP_SERVERS_CONFIG);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_CONFIG_GROUP_ID_CONFIG);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, KAFKA_CONFIG_ENABLE_AUTO_COMMIT_CONFIG);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, KAFKA_CONFIG_AUTO_COMMIT_INTERVAL_MS_CONFIG);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, KAFKA_CONFIG_SESSION_TIMEOUT_MS_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                KAFKA_CONFIG_DESERIALIZER_CLASS_CONFIG);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                KAFKA_CONFIG_DESERIALIZER_CLASS_CONFIG);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KAFKA_CONFIG_AUTO_OFFSET_RESET_CONFIG);

        this.kafkaConsumer = new KafkaConsumer<>(props);
        this.topic = topic;
    }

    static int consumerNum = 0;

    public void consume() {
        System.out.println("topic: " + topic);
        kafkaConsumer.subscribe(Arrays.asList(topic));

        try {
            while (true) {// 轮询
                ConsumerRecords<Integer, String> records = kafkaConsumer.poll(Long.MAX_VALUE);
                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<Integer, String>> partitionRecords = records.records(partition);
                    for (ConsumerRecord<Integer, String> record : partitionRecords) {
                        // 可以自定义Handler,处理对应的TOPIC消息(partitionRecords.key())
                        System.out.println(record.offset() + ": " + record.value());
                    }
                    kafkaConsumer.commitSync();
                }
            }
        } finally {
            kafkaConsumer.close();
        }
    }

    public static void main(String[] args) {
        KafkaConsumerFactory kConsumer = new KafkaConsumerFactory("uaes-bigdata-01");
        kConsumer.consume();
    }

}
