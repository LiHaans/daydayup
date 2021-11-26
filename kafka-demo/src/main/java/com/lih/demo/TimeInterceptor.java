package com.lih.demo;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class TimeInterceptor implements ProducerInterceptor<String, String> {

    private static int successCount = 0;
    private static int errorCount = 0;

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        // 创建一个新的record的, 加上时间戳

        return new ProducerRecord<>(producerRecord.topic(), producerRecord.partition(), producerRecord.timestamp(),
                producerRecord.key(), System.currentTimeMillis() + "," + producerRecord.value().toString());
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if (e == null ) {
            successCount++;
        } else {
            errorCount++;
        }
    }

    @Override
    public void close() {
        System.out.println("successCount: "+ successCount);
        System.out.println("errorCount: "+ errorCount);
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
