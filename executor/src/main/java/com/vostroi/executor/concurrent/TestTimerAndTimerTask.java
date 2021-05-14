package com.vostroi.executor.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Administrator
 * @date 2021/5/13 16:11
 * @projectName executor
 * @title: TestTimerAndTimerTask
 * @description: Timer 每次执行有1-2ms的延迟，任务执行次数越多，误差越大
 */
@Slf4j
public class TestTimerAndTimerTask {

    public static void main(String[] args) {
        demo01();
    }

    private static void demo01(){
        // idea提示用 ScheduledExecutorService 代替Timer
        // 20*1000 第一次执行延迟多长时间（ms），40 间隔时间（ms）
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("{}",System.currentTimeMillis());
            }
        }, 20*1000, 40);
    }

}
