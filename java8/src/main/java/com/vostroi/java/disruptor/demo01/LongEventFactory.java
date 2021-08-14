package com.vostroi.java.disruptor.demo01;

import com.lmax.disruptor.EventFactory;

/**
 * @author Administrator
 * @date 2021/6/15 16:56
 * @projectName java8
 * @title: LongEventFactory
 * @description: 由于需要 Disruptor 来为我们创建事件，因此我们要声明一个 EventFactory 来实例化 Event 对象
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
