package com.vostroi.java.countDownLatch.custom;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 * @date 2021/12/9 22:44
 * @projectName java8
 * @title: MyThreadCountDownKit
 * @description: 线程计数器
 */
@Slf4j
public final class MyThreadCountDownKit {
    /**
     * 计数器
     */
    private AtomicInteger counter;

    /**
     * 通知对象
     */
    private Object notify;

    /**
     * 回调接口， 用于线程执行完成处理业务
     */
    private Notify notifyListen;

    public void setNotifyListen(Notify notifyListen) {
        this.notifyListen = notifyListen;
    }

    /**
     * 构造方法
     * @param num
     */
    public MyThreadCountDownKit(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("num must > 0");
        }

        counter = new AtomicInteger(num);

        notify = new Object();
    }

    /**
     * 线程完成后 计数器减 1
     */
    public void countDown() {
        log.info("countDown count={}",counter.get());
        if (this.counter.get() <= 0) {
            return;
        }

        int count = this.counter.decrementAndGet();
        if (count < 0) {
            throw new RuntimeException("计数器错误");
        }

        if (count == 0) {
            synchronized (notify) {
                notify.notify();
            }
        }
    }

    /**
     * 等待所有 线程完成
     */
    public void await() throws InterruptedException {
        synchronized (notify) {
            while (counter.get() > 0) {
                log.info("counter={}", counter.get());
                notify.wait();
            }

            // 线程全部执行完成  回调
            if (notifyListen != null) {
                notifyListen.notifyListen();
            }
        }
    }


}
