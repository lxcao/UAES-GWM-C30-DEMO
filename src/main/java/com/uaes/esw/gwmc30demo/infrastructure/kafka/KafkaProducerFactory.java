package com.uaes.esw.gwmc30demo.infrastructure.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

import static com.uaes.esw.gwmc30demo.constant.InfraKafkaConstants.*;

public class KafkaProducerFactory {

    private String broker_list;
    private KafkaProducer<Integer, String> producer;
    private String client_id;
    private String topic;

    public KafkaProducerFactory(String client_id, String topic) {
        this.broker_list = KAFKA_CONFIG_BOOTSTRAP_SERVERS_CONFIG;
        this.client_id = client_id;
        this.topic = topic;
    }

    private KafkaProducer<Integer, String> createProducer(String broker_list, String client_id) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, broker_list);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, this.client_id);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KAFKA_CONFIG_KEY_DESERIALIZER_CLASS_CONFIG);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KAFKA_CONFIG_VALUE_DESERIALIZER_CLASS_CONFIG);
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);
        return producer;
    }

    public void sendMessage(int key, String msg) throws Exception {
        //System.out.println(this.broker_list);
        //System.out.println(this.client_id);
        this.producer = createProducer(this.broker_list, this.client_id);

        this.producer.send(new ProducerRecord<>(this.topic, key, msg)).get();

    }

    public void close() {
        this.producer.close();
        if (this.producer != null) {
            this.producer = null;
        }
    }

    public static void main(String[] args) {

        KafkaProducerFactory kafka = new KafkaProducerFactory("uaes-poc-test001", "uaes-bigdata-01");
        System.out.println("try to send message");
        try {
            kafka.sendMessage(1, "testMessage1");

            System.out.println("success");
        } catch (Exception e) {
            System.out.println("fail");

            e.printStackTrace();
        }
    }
}
