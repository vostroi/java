package com.vostroi.java8.inters;

import com.vostroi.java8.beans.Customer;

/**
 * @author Administrator
 * @date 2021/2/7 17:34
 * @projectName java8
 * @title: CustomerFactory
 * @description: TODO
 */
@FunctionalInterface
public interface CustomerFactory {
    /**
     * @param age
     * @param height
     * @return
     */
    Customer getInstance(int age, int height);
}
