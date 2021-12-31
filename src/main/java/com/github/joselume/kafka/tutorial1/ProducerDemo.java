package com.github.joselume.kafka.tutorial1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * BEST OPTION AS TEMPLATE. The second better would be ProducerDemoWithCallback, the only difference is that the second
 * has a callback to get some data for traceability
 */
public class ProducerDemo {
    public static void main(String[] args) {
        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        // Create producer record
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "hello_world");
        // send data
        producer.send(record);
        // flush data
        producer.flush();
        // flush data and close producer
        producer.close();

    }
}
