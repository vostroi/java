package com.vostroi.java8.features.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2021/2/19 15:32
 * @projectName java8
 * @title: Employee
 * @description: TODO
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    private Integer id;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private String gender;
    private String firstName;
    private String lastName;
}
