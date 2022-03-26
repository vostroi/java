package com.vostroi.java.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Administrator
 * @date 2021/12/24 17:34
 * @projectName java8
 * @title: SpiTest
 * @description: 注意 SPI 的文件要放在 resources/META-INF/services/ 路径下
 */
public class SpiTest {

    public static void main(String[] args) {
        // 会将 配置文件中配置的所有实现类都实例化
        // 所以 dubbo 没使用 JAVA SPI ， 自定义了 SPI 来定制 实例化类
        ServiceLoader<Say> load = ServiceLoader.load(Say.class);
        Iterator<Say> iterator = load.iterator();

        while (iterator.hasNext()) {
            Say s = iterator.next();
            s.say(System.currentTimeMillis()+"");
        }
    }

}
