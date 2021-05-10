package com.vostroi.java8.threadlocal;

import com.vostroi.java8.beans.Customer;
import com.vostroi.java8.threadlocal.bean.ThreadLocalBean;

/**
 * @author Administrator
 * @date 2021/3/24 11:13
 * @projectName java8
 * @title: Demo01
 * @description: TODO
 */
public class Demo01 {

    public static void main(String[] args) {
        ThreadLocalBean bean = new ThreadLocalBean("ThreadLocalBean名称", "ThreadLocalBean");
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                bean.setBean();
                bean.setName(Thread.currentThread().getName());
                bean.printSelf();
            });
            thread.setName("线程 ---"+i);
            thread.start();
        }

    }

}
