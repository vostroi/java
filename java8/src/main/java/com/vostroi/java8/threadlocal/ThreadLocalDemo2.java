package com.vostroi.java8.threadlocal;

import lombok.extern.slf4j.Slf4j;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @date 2021/12/15 17:14
 * @projectName java8
 * @title: ThreadLocalDemo2
 * @description: 模拟 导致内存泄露
 */
@Slf4j
public class ThreadLocalDemo2 {

    // 初始化一个线程池
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(15);


        for (int i = 0; i < 2000; i++) {
            int finalI = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    ThreadLocal<BigObject> tl = new ThreadLocal();

                    tl.set(new BigObject());

                    log.info(Thread.currentThread().getName() + "    i=" + finalI);

                    // 不释放 会一内存泄漏  GC时， key被回收了 对应的value没被回收
                    //tl.remove();

                }
            });
        }

        // 不shutdown 线程池中的线程一直在等待（5个核心线程存活） 程序不会结束 来观察产生对象回收情况
        //threadPool.shutdown();
    }




    // 100M 大对象
    static class BigObject {
        private byte[] bytes = new byte[1024 * 1024 * 100];
    }

}
