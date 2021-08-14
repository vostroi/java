package com.vostroi.java.disruptor.demo01;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @date 2021/8/5 23:28
 * @projectName java8
 * @title: LongEventWithMethodRef
 * @description: 由于在Java 8中方法引用也是一个lambda
 */
@Slf4j
public class LongEventWithMethodRef {

    // 作用与EventHandler.onEvent()一样
    public static void handleEvent(LongEvent event , long sequence, boolean endOfBatch){
        log.info("sequence={},endOfBatch={}",sequence , endOfBatch);
        log.info("LongEvent={}",event.getValue());
    }

    // 作用与 Translator.translateTo()一样
    public static void translateTo(LongEvent longEvent, long sequence, ByteBuffer bb) {
        longEvent.setValue(bb.getLong(0));
    }

    public static void main(String[] args) throws InterruptedException {

        int bufferSize = 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, Executors.defaultThreadFactory());

        /*
         * 指定生产者模式 单一 或 多生产者 ProducerType.SINGLE
         * 指定等待策略 WaitStrategy waitStrategy
         *  1. 默认等等策略 BlockingWaitStrategy 最慢的等待策略，但也是CPU使用率最低和最稳定的选项  （Java基本的同步方法）
         *  2. SleepingWaitStrategy 最好用在不需要低延迟，而且事件发布对于生产者的影响比较小的情况下。比如异步日志功能。
         *  3. YieldingWaitStrategy 可以被用在低延迟系统中的两个策略之一，这种策略在减低系统延迟的同时也会增加CPU运算量
         *  4. BusySpinWaitStrategy 是性能最高的等待策略，同时也是对部署环境要求最高的策略
         */
        // Disruptor<LongEvent> disruptor2 = new Disruptor<>(LongEvent::new, bufferSize, Executors.defaultThreadFactory(), ProducerType.SINGLE, new BlockingWaitStrategy());

        // java 8 lambda 方法引用
        disruptor.handleEventsWith(LongEventWithMethodRef::handleEvent);

        RingBuffer<LongEvent> ringBuffer = disruptor.start();

        ByteBuffer bb = ByteBuffer.allocate(8);

        for(long l=0; true; l++){
            bb.putLong(0, l);

            ringBuffer.publishEvent(LongEventWithMethodRef::translateTo, bb);

            TimeUnit.SECONDS.sleep(1);
        }
    }


}
