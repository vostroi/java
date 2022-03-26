package com.vostroi.java.spi;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * @date 2021/12/24 17:33
 * @projectName java8
 * @title: SayEnglish
 * @description: TODO
 */
@Slf4j
public class SayEnglish implements Say {
    @Override
    public void say(String words) {
        log.info("Hello .");
    }
}
