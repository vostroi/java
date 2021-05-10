package com.vostroi.java8.inters;

/**
 * @author Administrator
 * @date 2021/2/7 17:37
 * @projectName java8
 * @title: ArrayFactory
 * @description: TODO
 */
@FunctionalInterface
public interface ArrayFactory {
    /**
     * 创建整形数组
     * @param length
     * @return
     */
    int[] build(int length);
}
