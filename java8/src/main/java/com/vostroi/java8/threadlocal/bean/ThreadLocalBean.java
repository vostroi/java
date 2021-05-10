package com.vostroi.java8.threadlocal.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * @date 2021/3/24 11:14
 * @projectName java8
 * @title: ThreadLocalBean
 * @description:
 * ThreadLocal:
 *  1.最好定义为 private static final
 *  2.线程间数据隔离，
 *  3.同一线程 不同方法  组件之间 传递参数（同一线程内共享数据）
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadLocalBean {
    private static final ThreadLocal<ThreadLocalBean> tl = new ThreadLocal<>();

    private String name;

    private String code;

    public void printSelf() {
        log.info("当前线程：{},bean:{}",Thread.currentThread().getName() ,  tl.get().toString());
    }

    public void setBean() {
        tl.set(this);
    }
}
