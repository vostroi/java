package com.vostroi.java8.threadlocal.bean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * @date 2021/12/16 16:41
 * @projectName java8
 * @title: ThreadLocalUtil
 * @description: 线程间传参 工具类
 */
@Slf4j
public class ThreadLocalUtil {

    private static final ThreadLocal<ThreadLocalBean> tl = new ThreadLocal();

    public static void set(ThreadLocalBean tlb) {
        tl.set(tlb);
    }

    public static ThreadLocalBean get() {
        return tl.get();
    }

    public static void remove() {
        tl.remove();
    }

}
