package com.vostroi.executor.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author Administrator
 * @date 2021/5/13 15:42
 * @projectName executor
 * @title: TestScheduledThreadPoolExecutor
 * @description: 多线程的定时任务或者定期执行任务
 * 1. 可以利用Executors工具类来创建：
 *      1.1 ScheduledThreadPoolExecutor：用于若干个（固定）线程延时 或者定期执行任务，同时根据实际资源情况限定后台线程数量
 *          1.1.1 把任务封装成 ScheduledFutureTask，通过 scheduleAtFixedRate 或者 scheduledWithFixedDelay 方法向阻塞队列添加一个实现了RunnableScheduledFutureTask接口的ScheduleFutureTask类
 *          1.1.2 ScheduleFutureTask有3个成员变量，sequenceNumber 序列号，time 该任务将要执行的时间点，period 任务执行间隔周期
 *          1.1.3 ScheduledFutureTask的阻塞队列 由 DelayQueue（是一个无界队列，所以在 ScheduledThreadPoolExecutor 中 maximumPoolSize无意义） 实现，可以实现元素延时 一定时间后才能获取，DelayQueue 封装了 PriorityQueue，来对任务排序，先按 time 排序， time 一样，sequenceNumber 小的在前
 *          1.1.4 流程
 *              当调用ScheduledThreadPoolExecutor的scheduleAtFixedRate()方法或者scheduleWithFixedDelay()方法时，会向ScheduledThreadPoolExecutor的DelayQueue添加一个实现了RunnableScheduledFuture接口的ScheduleFutureTask。
 *              线程池中的线程从 DelayQueue 中获取ScheduleFutureTask，然后执行任务。
 *
 *      1.2 SingleThreadScheduledExecutor：适用于单个线程延时 或者定期执行任务，同时需要保证各个任务顺序执行的场景
 * 2. ScheduledThreadPoolExecutor 偶尔有1ms延迟，比Timer更精准
 * 3. scheduleAtFixedRate：以固定频率执行任务，若当前任务耗时较长，超过了period周期，则当前任务结束后会立即开始执行下一个任务
 * 4. scheduledWithFixedDelay：以固定延时执行任务，任务执行的间隔时间始终固定，延时是相对上一任务执行结束点而言
 */
@Slf4j
public class TestScheduledThreadPoolExecutor {

    public static void main(String[] args) {
        //demo01();

        // demo02();

        demo03();

        printSmt();
    }

    /**
     * 周期性执行任务
     */
    private static void demo01(){
        ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(3);
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                log.info("ScheduledThreadPoolExecutor Thread={},time={}", Thread.currentThread().getName(), time);

                String str = String.valueOf(time).substring(String.valueOf(time).length() - 2 , String.valueOf(time).length());
                log.info("str={}",str);
                if(Integer.valueOf(str) > 70){
                    // 出了异常会一直阻塞
                    // log.info("1/0={}",1/0);
                }
            }
        }, 5, 40, TimeUnit.MILLISECONDS); // 5 第一次执行的延时时间 ， 40 每次执行任务的间隔时间
    }


    private static void demo02(){
        // idea提示 使用 ThreadPoolExecutor 创建线程池，明确参数
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(2);
        // 只执行一次
        scheduled.schedule(new Runnable() {
            @Override
            public void run() {
                log.info("newScheduledThreadPool Thread={},time={}",Thread.currentThread().getName(), System.currentTimeMillis());
            }
        }, 10, TimeUnit.SECONDS);
    }

    private static void demo03(){
        ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
        scheduled.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                log.info("newSingleThreadScheduledExecutor Thread={},time={}", Thread.currentThread().getName(),System.currentTimeMillis());
            }
        }, 5, 10, TimeUnit.SECONDS);
    }

    private static void printSmt(){
        log.info("System time is {}，Thread={}", System.currentTimeMillis(),Thread.currentThread().getName());
    }
}

