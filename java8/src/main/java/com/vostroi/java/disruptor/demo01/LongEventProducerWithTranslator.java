package com.vostroi.java.disruptor.demo01;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author Administrator
 * @date 2021/6/15 17:24
 * @projectName java8
 * @title: LongEventProducerWithTranslator
 * @description: disruptor3后 更好的发布事件方式 提供了lambada式的API
 * 这样可以把一些复杂的操作放在Ring Buffer，所以在Disruptor3.0以后的版本最好使用Event Publisher或者Event Translator来发布事件
 */
public class LongEventProducerWithTranslator {
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 一个translator 可以看作是一个事件初始化器，publishEvent会调用它
     */
    private static final EventTranslatorOneArg<LongEvent , ByteBuffer> TRANSLATOR_ONE_ARG = new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
        @Override
        public void translateTo(LongEvent longEvent, long sequence, ByteBuffer bb) {
            longEvent.setValue(bb.getLong(0));
        }
    };

    public void onData(ByteBuffer bb){
        ringBuffer.publishEvent(TRANSLATOR_ONE_ARG , bb);
    }

}
