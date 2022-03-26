package com.vostroi.java.leetcode.digui;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @date 2021/12/20 17:00
 * @projectName java8
 * @title: Frog01
 * @description:
 *     要想跳到第10级台阶，要么是先跳到第9级，然后再跳1级台阶上去;要么是先跳到第8级，然后一次迈2级台阶上去。
 *     同理，要想跳到第9级台阶，要么是先跳到第8级，然后再跳1级台阶上去;要么是先跳到第7级，然后一次迈2级台阶上去。
 *     要想跳到第8级台阶，要么是先跳到第7级，然后再跳1级台阶上去;要么是先跳到第6级，然后一次迈2级台阶上去。
 */
@Slf4j
public class Frog {
    /**
     * jumpWays2 使用
     */
    public static Map<Integer , Integer> calcResult2 = new HashMap<>();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int result = 0;


        start = System.currentTimeMillis();
        result = jumpWays2(5000);
        log.info("jumpWays2 result={}, time cost={}",result ,System.currentTimeMillis() - start);


        start = System.currentTimeMillis();
        result = jumpWays3(5000);
        log.info("jumpWays3 result={}, time cost={}",result ,System.currentTimeMillis() - start);


        result = jumpWays1(50);
        log.info("jumpWays1 result={}, time cost={}",result ,System.currentTimeMillis() - start);
    }


    /**
     * 递归去做，暴力运算 时间复杂度 2^n
     * 时间复杂度太高，30个台阶就需要10秒
     * @param steps  共有多少台阶
     * @return
     */
    public static int jumpWays1(int steps) {
        //log.info("jumpWays steps={}" , steps);
        if (steps == 1) {
            return 1;
        } else if (steps == 2) {
            return 2;
        } else {
            return jumpWays1(steps - 2) + jumpWays1(steps - 1);
        }

    }

    /**
     * 优化：目的是减少重复计算 时间复杂度是 n
     * 在 jumpWays1 中， 从日志可看出来，大量的重复计算 考虑将第个台阶的计算结果一个缓存，下次遇到直接从缓存中取，不再计算
     * @param steps
     * @return
     */
    public static int jumpWays2(int steps) {
        //log.info("jumpWays2 steps={}" , steps);

        if (calcResult2.containsKey(steps)) {
            return calcResult2.get(steps);
        }

        if (steps == 1) {
            return 1;
        } else if (steps == 2) {
            return 2;
        } else {
            int result = jumpWays2(steps - 2) + jumpWays2(steps - 1);

            if (!calcResult2.containsKey(steps)) {
                calcResult2.put(steps, result);
            }

            return result;
        }

    }


    /**
     * 优化：动态规划 时间复杂度 n
     * 与 jumpWays2 区别
     *  jumpWays2：是自顶向下 从 steps-1 -> steps-2 -> steps - 3 ---------> 2 -> 1
     *  动态规划： 自底向上， 动态规划最核心的思想，就在于拆分子问题，记住过往，减少重复计算 （斐波那契数列）
     * @param steps
     * @return
     */
    public static int jumpWays3(int steps) {
        //log.info("jumpWays3 steps={}" , steps);

        if (steps <= 1) {
            return 1;
        }
        if (steps == 2) {
            return 2;
        }

        // 代表 steps=1时 跳法
        int a = 1;
        // 代表 steps=2时 跳法
        int b = 2;
        int result = 0;

        for (int i = 3; i <= steps; i++) {
            result = a + b;

            a = b ;
            b = result;
        }

        return result;

    }


}
