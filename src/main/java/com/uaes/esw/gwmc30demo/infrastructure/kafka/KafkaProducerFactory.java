package com.uaes.esw.gwmc30demo.infrastructure.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

import static com.uaes.esw.gwmc30demo.constant.InfraKafkaConstants.*;

public class KafkaProducerFactory {

    private static  KafkaProducer<String, String> producer;

    private static KafkaProducer<String, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONFIG_BOOTSTRAP_SERVERS_CONFIG);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, KAFKA_CONFIG_CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KAFKA_CONFIG_SERIALIZER_CLASS_CONFIG);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KAFKA_CONFIG_SERIALIZER_CLASS_CONFIG);
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        return producer;
    }

    public static void sendMessage(String key, String msg) {
        try{
            producer = getKafkaProducer();
            //System.out.println(this.broker_list);
            //System.out.println(this.client_id);
            producer.send(new ProducerRecord<>(KAFKA_CONFIG_TOPIC, key, msg)).get();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void closeKafkaProducer() {
        producer.close();
        if (producer != null) {
            producer = null;
        }
    }

    private static KafkaProducer<String, String> getKafkaProducer(){
        if(producer == null){
            producer = createProducer();
        }
        return producer;
    }

    public static void main(String[] args) {
        System.out.println("try to send message");
        try {
            sendMessage("1", "testMessage1");

            System.out.println("success");
        } catch (Exception e) {
            System.out.println("fail");

            e.printStackTrace();
        }
    }
}
