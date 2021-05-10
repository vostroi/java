package com.vostroi.java8.inters;

/**
 * @author Administrator
 * @date 2021/2/7 10:02
 * @projectName java8
 * @title: Printer
 * @description: FunctionalInterface 注解是非必须的（JVM会自动判断），标记为该接口为函数式接口，避免他人向接口中添加方法； 若要增加其它方法，其它方法必须是default  或者  static
 *               FunctionalInterface 接口中只有一个方法 比如java.lang.Runnable和java.util.Comparator
 */
@FunctionalInterface
public interface Printer {
    /**
     * 打印
     * @param content
     */
    void print(String content) ;

    /**
     * 函数式接口其它方法， 用static修饰
     */
    static void otherStatic() {
    }

    /**
     * 函数式接口其它方法 用default修饰
     */
    default void otherDefault() {
    }
}
