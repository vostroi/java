package com.vostroi.socket.dubbo;

import com.vostroi.socket.dubbo.simulate.RPC;
import com.vostroi.socket.dubbo.simulate.SayService;
import com.vostroi.socket.dubbo.simulate.SayServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * @date 2021/12/22 16:17
 * @projectName java8
 * @title: Simulate
 * @description: 模拟 RPC  利用 socket
 */
@Slf4j
public class SimulateClient {


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            long start = System.currentTimeMillis();

            SayService client = RPC.refer(SayService.class, "192.168.70.1", 18181);

            String retStr = client.say("烦得批爆");

            log.info("retStr={},cost={}", retStr, System.currentTimeMillis() - start);
        }
    }

}
