package com.vostroi.java.countDownLatch.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @date 2021/12/9 23:26
 * @projectName java8
 * @title: Test
 * @description: 测试 juc CountDownLatch
 */
@Slf4j
public class Test {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        log.info("start...");


        // 创建3个线程
        Thread thread1 = new Thread(() -> {
            log.info("Thread 1 running");

            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            latch.countDown();
        });

        Thread thread2 = new Thread(() -> {
            log.info("Thread 2 running");

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            latch.countDown();
        });


        Thread thread3 = new Thread(() -> {
            log.info("Thread 3 running");

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            latch.countDown();
        });


        thread1.start();
        thread2.start();
        thread3.start();

        // 注释后， 主线程不会没阻塞
        latch.await();
        // 带阻塞时间 超时自动唤醒
        //latch.await(100, TimeUnit.SECONDS);

        log.info("Main Thread Finished...");

        
    }
    
    
}
