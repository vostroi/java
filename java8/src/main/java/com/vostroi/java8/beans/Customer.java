package com.vostroi.java8.beans;

import lombok.Data;

import java.util.Objects;

/**
 * @author Administrator
 * @date 2021/2/7 9:42
 * @projectName java8
 * @title: Customer
 * @description: TODO
 */
@Data
public class Customer {

    private String id ;

    private String nickName ;

    private String password ;

    private int age ;

    private int height ;

    public Customer(int age, int height) {
        this.age = age;
        this.height = height;
    }

    public Customer() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return age == customer.age &&
                height == customer.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, height);
    }
}
