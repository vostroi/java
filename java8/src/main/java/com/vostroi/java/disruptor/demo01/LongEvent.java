package com.vostroi.java.disruptor.demo01;

import lombok.Data;

/**
 * @author Administrator
 * @date 2021/6/15 16:54
 * @projectName java8
 * @title: LongEvent
 * @description: hello world 案例
 * 生产者传递一个long值给消费者，消费都消费这个long（打印）
 * 首先声明一个Event来包含要传递的数据 LongEvent
 */
@Data
public class LongEvent {
    private Long value;




}
