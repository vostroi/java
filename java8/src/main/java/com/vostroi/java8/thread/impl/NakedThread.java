package com.vostroi.java8.thread.impl;

import com.vostroi.java8.thread.base.EngineUtils;
import com.vostroi.java8.thread.base.IFlavorDemo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Administrator
 * @date 2021/3/16 11:17
 * @projectName java8
 * @title: NakedThread
 * @description: 并发4种方式一：裸线程 对应操作系统的线程，JVM管理线程的生命周期，若不需要线程间通信，无需关注线程调度问题 每个线程有自己的栈空间
 */
@Slf4j
public class NakedThread implements IFlavorDemo {

    /**
     * 为每个 engine 创建一个线程
     * 查询结果放在 AtomicReference (不需要锁或其它机制来保证并发的写)
     * @param keywords 搜索的问题
     * @param engines
     * @return 最先查询出的结果
     */
    @Override
    public String getFirstResult(String keywords, List<String> engines) {
        // 存放查询结果，保证原子性
        AtomicReference<String> result = new AtomicReference<String>();
        log.info("通过裸线程并发测试 控制数量:{}", engines.size());

        // 使用原子变量测试 裸线程创建是否有序
        AtomicInteger count = new AtomicInteger(1);
        // 为每个搜索引擎创建线程
        engines.forEach(e->{

            new Thread(()->{
                // 如果result.get为null , 则 将 result set 为  EngineUtils.seach(keywords, e)
                result.compareAndSet(null, EngineUtils.seach(keywords, e));
            }).start();;

            log.info("第{}个线程，为搜索引擎【{}】创建", count , e);
            count.getAndIncrement();
        });

        while (null == result.get()) {
            // 死循环直到 result 有结果 立即退出循环
        }
        return result.get();
    }

    public static void main(String[] args) {
        String result = new NakedThread().getFirstResult("Java 线程教程", EngineUtils.getEnineList());

        log.info(result);

    }

}
