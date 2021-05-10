package com.vostroi.java8.features.foreach;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2021/2/23 17:43
 * @projectName java8
 * @title: DemoForEach1
 * @description: java 8 foreach 是一个工具方法用于遍历 集合 list set map  和 stream 流
 */
public class DemoForEach1 {

    private static final String[] featuresArras = {"Stack","James", "Mary", "John", "Patricia", "Robert", "Anny", "Linda","patricia"};
    private static final List<String> featuresList = Arrays.asList(featuresArras);
    private static final Set<String> featuresSet = new HashSet<>(featuresList);

    public static void main(String[] args) {
        // 参数 action 表示 它接受一个 有单个参数且无返回值的操作（函数体） 经是 Consumer  的一个实例
        featuresList.forEach(System.out :: println);
        // 遍历时做一些过滤操作
        featuresList.stream().filter(f->f.length()>2).forEach(System.out::println);
        featuresList.forEach(f-> {
            String s = f.toUpperCase();
            System.out.println(s);
        });
        // 也可以单独把 action 拿出来
        Consumer<String> action = new Consumer<String>() {
            @Override
            public void accept(String str) {
                System.out.println(str.toLowerCase());
            }
        };
        featuresList.forEach(action);



        Map<Integer, String> map = featuresList.stream().collect(Collectors.toMap(f -> f.length(), f -> f, (o, n) -> o));
        // map 的 foreach 对 map 的每个 entry 进行遍历 直到所有 entry 处理完 或者  异常
        map.forEach((k, v) -> System.out.println("[key=" + k + ",value=" + v+"]"));
        // 同样 也可以把 action 单独拿出来
        BiConsumer<Integer , String> biAction = new BiConsumer<Integer, String>() {
            @Override
            public void accept(Integer k, String v) {
                System.out.println("[key=" + k + ",value=" + v+"]");
            }
        };
        map.forEach(biAction);

        map.entrySet().forEach(System.out::println);
        map.keySet().forEach(System.out::println);
        map.values().forEach(System.out::println);


    }

}
