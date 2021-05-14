package com.vostroi.executor.concurrent;

import java.util.concurrent.*;

/**
 * @author Administrator
 * @date 2021/5/12 13:50
 * @projectName executor
 * @title: TestExecutor
 * @description: Executors 创建线程池
 * Executors.fixedthreadpool()
 * Executors.singlethreadexecutor()
 * Executors.cachedthreadpool()
 * 1. 线程数量确定
 *      线程耗时短：2*CPU数（CPU线程数）
 *      线程耗时长：CPU数（CPU线程数）
 * 2. submit()用于有返回值线程提交，参数Callable，future.get()获取线程返回值
 * 3. execute()用于无返回值线程提交，参数Runnable
 */
public class TestExecutor {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 100, TimeUnit.SECONDS , new ArrayBlockingQueue<Runnable>(5));
        Future future = threadPoolExecutor.submit(new CallableTest());
        try {
            System.out.println(future.get());
            System.out.println("CPU数量"+Runtime.getRuntime().availableProcessors());
            System.out.println(1234.56 % 360);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }
    }
}
