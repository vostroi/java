package com.vostroi.executor.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;

/**
 * @author Administrator
 * @date 2021/5/12 10:49
 * @projectName executor
 * @title: TestExchanger
 * @description: 线程间数据交换的工具：提供一个同步点，在这个同步点，两个线程可以交换彼此数据，通过excahange()方法实现；若线程A先到达同步点，调用excahange()，会一直阻塞等待线程B达到同步点，线程B调用excahange()来交换数据
 * 1. 重点是交换数据 所以必须是成双成对的
 * 2. Exchanger可以看作是一个双向的同步队列，一个线程从队列头进行操作，一个线程从 尾部进行操作
 * 3. 若不想一直等待，excahange()可以设置超时时间
 */
@Slf4j
public class TestExchanger {

    // 初始化
    private static Exchanger<String> exchanger = new Exchanger();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String strA = "银行流水 A" ;
                try {
                    log.info("Thread={}",Thread.currentThread().getName());
                    // 在要交换数据的同步点，调用 exchanger.exchange()
                    String strB = exchanger.exchange(strA);
                    log.info("线程{},strA={},strB={},strA=strB={}",Thread.currentThread().getName(),strA,strB,strA.equals(strB));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                String strB = "银行流水 B" ;
                try {
                    log.info("Thread={}",Thread.currentThread().getName());
                    Thread.sleep(3000);
                    // 在要交换数据的同步点，调用 exchanger.exchange()
                    String strA = exchanger.exchange(strB);
                    log.info("线程{},strA={},strB={},strA=strB={}",Thread.currentThread().getName(),strA,strB,strA.equals(strB));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
