package com.vostroi.java8.threadlocal;

import cn.hutool.db.ds.simple.SimpleDataSource;
import com.vostroi.java8.threadlocal.bean.ThreadLocalBean;

import java.text.SimpleDateFormat;

/**
 * @author Administrator
 * @date 2021/3/24 11:13
 * @projectName java8
 * @title: Demo01
 * @description: TODO
 */
public class Demo01 {
    private static final ThreadLocal<SimpleDateFormat> formatter = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue()
        {
            return new SimpleDateFormat("yyyyMMdd HHmm");
        }
    };

    // 与上面代码块等效
    private static final ThreadLocal<SimpleDateFormat> formatter2 = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy"));

    public static void main(String[] args) {
        ThreadLocalBean bean = new ThreadLocalBean("ThreadLocalBean名称", "ThreadLocalBean");
        //ThreadLocal<ThreadLocalBean> sThreadLocal = ThreadLocal.withInitial(()->new ThreadLocalBean());




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
