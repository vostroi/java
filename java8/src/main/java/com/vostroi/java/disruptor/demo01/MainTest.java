package com.vostroi.java.disruptor.demo01;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Administrator
 * @date 2021/6/15 17:33
 * @projectName java8
 * @title: MainTest
 * @description: 测试
 */
@Slf4j
public class MainTest {

    public static void main(String[] args) throws InterruptedException {
        LongEventFactory factory = new LongEventFactory();
        // 必须是2次幂
        int bufferSize = 1024;
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        // 构造 Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, threadFactory);
        // 注册handler
        disruptor.handleEventsWith(new LongEventHandler());
        // ringBuffer 可以用来关闭队列
        RingBuffer<LongEvent> ringBuffer = disruptor.start();

//        LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);
        for(long l=0 ; true; l++){
            bb.putLong(0,l);
            producer.onData(bb);
            log.info("publish event success");
            Thread.sleep(3000);
        }
    }
}
