package com.vostroi.executor.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Administrator
 * @date 2021/5/10 16:39
 * @projectName executor
 * @title: TestCyclicBarrier
 * @description: 同步屏障  让一组线程达到一个屏障（或者叫同步点）时阻塞（有点像执行到一定位置，暂停执行），直到所有线程（参数parties）达到同步屏障后，同步屏障开门，所有线程继续执行
 * 使用场景：可用于多线程计算数据，最后合并数据
 * 1. 两种使用方式（两种构造方法）
 *      1.1 public CyclicBarrier(int parties)
 *      1.2 public CyclicBarrier(int parties, Runnable barrierAction) 参数分别是 要阻塞在同步屏障的线程数量 parties 和 Runnable对象（作用是处理：（如果需要）当需要在最后一个线程达到屏障前处理一个任务，这个任务就是Runnable）
 * 2. CyclicBarrier 的计数器可以重置
 * 3. 在所有线程达到同步屏障后，并不意味着所有线程“同时”开始执行，而是使所有线程的启动时间降到最低
 * 4. barrier2.await()的调用次数多于，少于parties 都达不到想要结果
 * 5. await方法抛出的异常。可能导致异常的原因是 这一组线程有可能有线程await方法被中断或者超时
 * 6. 方法介绍
 *      getWaitingNumber() 返回被阻塞在同步屏障的线程数
 *      isBroken() 是否有阻塞线程被中断
 */
@Slf4j
public class TestCyclicBarrier {
    // 要阻塞在同步屏障的线程数量
    private static CyclicBarrier barrier1 = new CyclicBarrier(2);
    private static CyclicBarrier barrier2 = new CyclicBarrier(3, new DoSomething());


    public static void main(String[] args) {

        testBarrier1();
//        testBarrier2();
    }

    private static void testBarrier1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 在每个在阻塞在同步屏障的子线程中调用await()
                    barrier1.await();
                    log.info("testBarrier1 barrier1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }


            }
        }).start();

        try {
             // 这里注释后，子线程会一直阻塞，因为 barrier1 需要2个线程都达到屏障
             barrier1.await();
            System.out.println("main 1");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试用 静态内部类
     */
    @Slf4j
    static class DoSomething implements Runnable{

        @Override
        public void run() {
            System.out.println("DoSomething run before CyclicBarrier...");
        }
    }

    private static void testBarrier2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 在每个在阻塞在同步屏障的子线程中调用await()
                try {
                    barrier2.await();
                    log.info("testBarrier2 barrier2 1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 在每个在阻塞在同步屏障的子线程中调用await()
                    barrier2.await();
                    log.info("testBarrier2 barrier2 2");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 在每个在阻塞在同步屏障的子线程中调用await()
                    barrier2.await();
                    log.info("testBarrier2 barrier2 3");
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();

        try {
//            barrier2.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("main 2");

    }


}
