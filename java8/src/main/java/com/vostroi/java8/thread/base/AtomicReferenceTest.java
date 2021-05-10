package com.vostroi.java8.thread.base;

import com.vostroi.java8.beans.Customer;

import javax.sound.midi.Soundbank;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Administrator
 * @date 2021/3/16 16:59
 * @projectName java8
 * @title: AtomicReferenceTest
 * @description:
 */
public class AtomicReferenceTest {
    /**
     * AtomicReference：
     *  与AtomicInteger类似，是对引用类型的封装，可以保证引用对象并发下的线程安全，
     *  提供一种读和写都是原子操作的对象引用变量
     *  1.AtomicReference.compareAndSet(V expect, V update)
     *      如果 AtomicReference 的值 与 expect 是相等（==）的， 刚返回true， 且将  AtomicReference 的值 更新为 update
     */
    public static void main(String[] args) {
        AtomicReference<String> name = new AtomicReference<>("AtomicReference");
        boolean b = name.compareAndSet("AtomicReference", new String("CADSASDF"));
        System.out.println(b);
        System.out.println(name);
        System.out.println(name.get());
        name.set("东西南北中");
        System.out.println(name.get());
    }
}
