package com.vostroi.java.disruptor.demo01;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 * @date 2021/6/15 18:01
 * @projectName java8
 * @title: MainTestJava8
 * @description: 使用 java8 简化
 */
@Slf4j
public class MainTestJava8 {

    public static void main(String[] args) throws InterruptedException {
        // 必须是2次幂
        int bufferSize = 1024;

        // 构造 Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, Executors.defaultThreadFactory());

        // 注册handler
        // disruptor.handleEventsWith(new LongEventHandler());
        // 使用 lambda 来注册 handler
        disruptor.handleEventsWith(
                (event , sequence , endOfBatch)->{
                    log.info("event1={}, sequence1={}, endOfBatch1={}",event , sequence , endOfBatch);
                    log.info("LongEvent1={}", event.getValue());
                }
        );
        // ringBuffer 可以用来关闭队列
        RingBuffer<LongEvent> ringBuffer = disruptor.start();
        //log.info("ringBuffer={}", ringBuffer);
       //log.info("ringBuffer={}", disruptor.getRingBuffer());

        LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);
        for(long l=0 ; true; l++){
            bb.putLong(0,l);

            // producer.onData(bb);

            // lambda 写法  效果与 producer.onData(bb); 一样
            ringBuffer.publishEvent( (event , equence , buffer)-> event.setValue(buffer.getLong(0)) , bb);

            log.info("publish event success");
            Thread.sleep(3000);
        }
    }


}
