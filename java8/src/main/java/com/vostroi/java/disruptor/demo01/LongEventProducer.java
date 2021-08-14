package com.vostroi.java.disruptor.demo01;

import com.lmax.disruptor.RingBuffer;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * @author Administrator
 * @date 2021/6/15 17:08
 * @projectName java8
 * @title: LongEventProducer
 * @description: 每个事件都有生成事件的源（谁产生事件），此处模拟磁盘IO或者network读取数据时候产生事件，事件源使用ByteBuffer来模拟它接收到的数据
 */

@Slf4j
public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 旧版本disruptor
     * 发布事件，每调用一次发布一次事件，参数会传递给消费者
     * 如果我们使用RingBuffer.next()获取一个事件槽，那么一定要发布对应的事件。如果不能发布事件，那么就会引起Disruptor状态的混乱。
     * 尤其是在多个事件生产者的情况下会导致事件消费者失速，从而不得不重启应用才能会恢复
     * try catch finally保证事件发布
     */
    public void onData(ByteBuffer bb){
        // 可以把 RingBuffer看作一个事件队列，next()取得下一个事件槽
        long sequence = ringBuffer.next();
        try {
            // 用上面的索引，取出一个空的事件，用于填充数据
            LongEvent longEvent = ringBuffer.get(sequence);
            longEvent.setValue(bb.getLong(0));
        } catch (Exception e) {
            log.error("发布事务异常：", e);
        } finally {
            // 发布事件
            ringBuffer.publish(sequence);
        }
    }


}
