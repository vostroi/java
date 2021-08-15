package com.vostroi.executor.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author Administrator
 * @date 2021/5/10 15:57
 * @projectName executor
 * @title: TestCountDownLatch
 * @description: CountDownLatch 闭锁：用来让一个线程等待一个或多个线程执行完成，类似于当前线程调用join()，让其它线程加入进当前线程，其它线程执行完再执行当前线程剩下的逻辑
 * 1. countDown() 和 await() 配合使用才能达到类似 join()效果
 * 2. 构造方法，要传入总共要等待的线程数量，也就是计数器
 * 3. countDown()方法可以用在任何地方，可以是某一个步骤点，也可以是一个线程，每次调用计数器减1，直到计数器为0
 * 4. 在阻塞（等待其它线程执行）的主线程中，调用 await()方法，等待调用了countDown()的线程，直到计数器为0，主线程开始执行剩下的逻辑，如果被等待的线程执行过久，主线程不想一直等待，可调用await(time,timeunit)
 * 5. 注意：计数器大于0时，主线程才会阻塞，一旦计数器为0，不再阻塞主线程，计数器只能初始化一次
 */
@Slf4j
public class TestCountDownLatch {

    // 初始化CountDownLatch 传入计数器值
    private static CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        log.info("主线程 开始时间{},开始执行...",start);

        // 子线程1
        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("子线程 1 创建 step 1");
                try {
                    Thread.sleep(10000);
                    log.info("子线程 1 执行逻辑 step 2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("子线程 1 执行完成 step 3");
                // 被等待的子线程或者操作执行完成后，计数器-1
                latch.countDown();
            }
        }).start();


        // 子线程2
        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("子线程 2 创建 step 1");
                try {
                    Thread.sleep(10000);
                    log.info("子线程 2 执行逻辑 step 2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("子线程 2 执行完成 step 3");
                // 被等待的子线程或者操作执行完成后，计数器-1
                latch.countDown();
            }
        }).start();

        long time2 = System.currentTimeMillis();
        log.info("主线程 耗时{},子线程创建完成... 等待了线程执行完毕...",time2-start);

        // 在等待的主线程中调用await()，直到CountDownLatch的计数器为0，再继续执行主线程的逻辑
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("主线程 耗时{}，执行结束...",System.currentTimeMillis()-start);


    }

}
