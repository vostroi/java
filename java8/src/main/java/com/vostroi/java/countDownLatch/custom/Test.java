package com.vostroi.java.countDownLatch.custom;

import com.vostroi.java8.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @date 2021/12/9 23:01
 * @projectName java8
 * @title: Test
 * @description: TODO
 */
@Slf4j
public class Test {

    public static void main(String[] args) throws InterruptedException {
        MyThreadCountDownKit countDownKit = new MyThreadCountDownKit(3);
        log.info("start...");

        //
        countDownKit.setNotifyListen(()->{
            log.info("All Threads Finished...");
        });

        // 创建3个线程
        Thread thread1 = new Thread(() -> {
            log.info("Thread 1 running");

            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            countDownKit.countDown();
        });

        Thread thread2 = new Thread(() -> {
            log.info("Thread 2 running");

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            countDownKit.countDown();
        });


        Thread thread3 = new Thread(() -> {
            log.info("Thread 3 running");

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            countDownKit.countDown();
        });


        thread1.start();
        thread2.start();
        thread3.start();

        // 注释后， 主线程不会没阻塞
        countDownKit.await();

        log.info("Main Thread Finished...");
    }

}
