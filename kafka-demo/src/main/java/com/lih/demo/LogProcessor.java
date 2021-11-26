package com.lih.demo;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.nio.charset.StandardCharsets;

public class LogProcessor implements Processor<byte[], byte[]> {

    private ProcessorContext processorContext;

    @Override
    public void init(ProcessorContext processorContext) {
        this.processorContext = processorContext;
    }

    @Override
    public void process(byte[] key, byte[] value) {
        System.out.println("1111");
        String input = new String(value);
        // 如果包含 >>> 则保留后面内容
        if (input.contains(">>>")) {
            String output = input.split(">>>")[1];
            processorContext.forward("logProcessor".getBytes(), output.getBytes(StandardCharsets.UTF_8));
        }else {
            processorContext.forward("logProcessor".getBytes(), value);
        }
    }

    @Override
    public void punctuate(long l) {

    }

    @Override
    public void close() {

    }
}
