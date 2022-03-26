package com.vostroi.socket.dubbo.simulate;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author Administrator
 * @date 2021/12/22 16:19
 * @projectName java8
 * @title: SayServiceImpl
 * @description: TODO
 */
@Slf4j
public class SayServiceImpl implements SayService {

    @Override
    public String say(String words) {
        log.info("Provider 接收到参数 words={}" , words);

        String rtn = UUID.randomUUID().toString();
        log.info("Provider 返回参数{}",rtn);
        return rtn;
    }
}
