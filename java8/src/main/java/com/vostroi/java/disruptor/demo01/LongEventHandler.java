package com.vostroi.java.disruptor.demo01;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * @date 2021/6/15 17:05
 * @projectName java8
 * @title: LongEventHandler
 * @description: 消费者（事件处理器，需要实现EventHandler接口），来消费（处理）事件
 */
@Slf4j
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        log.info("l={},b={}",l , b);
        log.info("LongEvent={}",longEvent.getValue());
    }
}
