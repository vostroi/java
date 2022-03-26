package com.vostroi.executor.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @author Administrator
 * @date 2021/5/11 11:23
 * @projectName executor
 * @title: Semaphore
 * @description: Semaphore 信号量：用于流量控制，用于访问特定资源的线程数据，保证合理的公共资源，
 * 方法介绍
 *  semaphore.acquire(); // 获得通行证，成功才继续执行，否则阻塞
 *  boolean semaphore.tryAcquire(); // 尝试获取1个通行证（可以传参数，获取多个通行证，指定超时时间-会阻塞一定时间，指定时间内获取不到返回false），获取不到返回false，不会阻塞等待，常用于if else业务
 *  int semaphore.availablePermits(); // 剩余可用通行证数量
 *  int semaphore.getQueueLength(); // 获取等待获取通行证队列（线程）的长度
 *  boolean semaphore.hasQueuedThreads(); // 是否有线程在等待获取通行证
 *  semaphore.release(); // 在处理完业务逻辑后，调用release()方法，释放通行证（semaphore不会将通行证与线程关联，所以可以在其它线程中释放通行证--分布式不同主机行不行未测试）
 */
@Slf4j
public class TestSemaphore {

    // 初始化 传入通行证数量  一次性最多允许 并行的线程数量 也就是 初始可用的通行证数量
    private static Semaphore semaphore = new Semaphore(4);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 在要占用公共资源的子线程业务逻辑之前，调用acquire()方法，获得通行证
                        semaphore.acquire();
                        log.info("{} is saving data... availablePermits={}, getQueueLength={}",Thread.currentThread().getName() , semaphore.availablePermits() , semaphore.getQueueLength());
                        Thread.sleep(3000);
                        // 在处理完业务逻辑后，调用release()方法，释放通行证（semaphore不会将通行证与线程关联，所以可以在其它线程中释放通行证--分布式不同主机行不行未测试）
                        semaphore.release();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }


        // 可以在其它线程释放通行证
//        for (int n = 0; n < 10; n++) {
//            try {
//                Thread.sleep(6000);
//                semaphore.release();
//                log.info("线程{}释放第{}个通行证",Thread.currentThread().getName(),n+1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

    }

}
