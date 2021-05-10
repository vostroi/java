package com.vostroi.java8.utils;

import java.util.Random;

/**
 * @author Administrator
 * @date 2021/2/7 17:18
 * @projectName java8
 * @title: CommonUtils
 * @description: TODO
 */
public class CommonUtils {

    public int randomA2B(int a, int b) {
        return new Random().nextInt(b - a + 1) + a;
    }
}
