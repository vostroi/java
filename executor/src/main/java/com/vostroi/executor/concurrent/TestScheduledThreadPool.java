package com.vostroi.executor.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Administrator
 * @date 2021/5/13 18:00
 * @projectName executor
 * @title: TestScheduledThreadPool
 * @description: 定时任务 限制执行次数
 *
 *
 * 限制程序执行的次数：如果是单线程，那么可以直接定义一个静态变量count，每执行一次，count加一，如果count大于某个值就调用shutdown或者shutdownNow函数；
 * 如果是多线程，稍微要复杂一点，但是原理也是一样的。定义一个静态变量count，没执行一个也是count加一，只不过在执行加一操作之前需要加锁，执行完之后需要解锁
 */
@Slf4j
public class TestScheduledThreadPool {

    //  限制执行次数
    private static AtomicInteger count = new AtomicInteger(0);
    private static final Integer MAX_COUNT = 10;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(5);
    private MyTask task = new MyTask();

    /**
     * 自定义任务单元
     */
    private class MyTask implements Runnable {
        @Override
        public void run() {
            lock.writeLock().lock();
            count.incrementAndGet();
            log.info("第{}次执行，当前线程：{}，当前时间：{}, count={}",count.toString(),Thread.currentThread().getName(),System.currentTimeMillis(), count.toString());
            lock.writeLock().unlock();
        }
    }

    public void start(){
        log.info("开始时间：{}", System.currentTimeMillis());
        scheduled.scheduleAtFixedRate(task, 10, 5, TimeUnit.SECONDS);

        // 判断执行次数
        try {
            while (!scheduled.isShutdown()){
                lock.readLock().lock();
                if(MAX_COUNT <= count.intValue()){
                    scheduled.shutdown();
                }
                lock.readLock().unlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        log.info("线程：{}，定期任务执行完成，count={}",Thread.currentThread().getName(), count);

    }


    public static void main(String[] args) {
        new TestScheduledThreadPool().start();
    }


}
