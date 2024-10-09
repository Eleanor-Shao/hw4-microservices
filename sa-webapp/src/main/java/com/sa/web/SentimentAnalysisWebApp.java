package com.sa.web;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

public class SentimentAnalysisWebApp {
    private KafkaProducer<String, String> producer;
    private static final String TOPIC = "webapp_to_logic";

    public SentimentAnalysisWebApp() {
        // Kafka producer configuration
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka-service.kafka-namespace.svc.cluster.local:9092"); // Adjust Kafka service address
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(props);
    }

    public void sendToKafka(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, message);
        producer.send(record);
    }

    // Call this method instead of making a direct HTTP call to Logic service
    public void handleRequest(String input) {
        sendToKafka(input);
        System.out.println("Sent to Kafka: " + input);
    }
}
