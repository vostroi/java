package com.vostroi.java8.threadlocal;

import cn.hutool.json.JSONUtil;
import com.vostroi.java8.threadlocal.bean.ThreadLocalBean;
import com.vostroi.java8.threadlocal.bean.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * @date 2021/12/16 16:28
 * @projectName java8
 * @title: ThreadLocalDemo3
 * @description: 测试方法传递
 */
@Slf4j
public class ThreadLocalDemo3 {

    public static void main(String[] args) {

        ThreadLocalUtil.set(new ThreadLocalBean("张三", "ZhangSan"));

        log.info("{} bean={}",Thread.currentThread().getName(), JSONUtil.toJsonStr(ThreadLocalUtil.get()));

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                ThreadLocalUtil.set(new ThreadLocalBean("张三三", "ZhangSanSan"));
                    new ThreadLocalDemo3().compose();
                }
            }).start();
        }

    }


    public void compose() {
        this.method1();


        this.method2();


        this.method3();
    }

    public void method1() {
        log.info("{} bean1={}",Thread.currentThread().getName(), JSONUtil.toJsonStr(ThreadLocalUtil.get()));
    }

    public void method2() {
        log.info("{} bean2={}",Thread.currentThread().getName(), JSONUtil.toJsonStr(ThreadLocalUtil.get()));

        ThreadLocalUtil.remove();

    }

    public void method3() {
        log.info("{} bean3={}",Thread.currentThread().getName(), JSONUtil.toJsonStr(ThreadLocalUtil.get()));

    }


}
