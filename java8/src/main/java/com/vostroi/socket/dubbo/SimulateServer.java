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
public class SimulateServer {


    public static void main(String[] args) {
        SayService service = new SayServiceImpl();

        RPC.exportServer(service, 18181);

        log.info("服务端启动成功");
    }

}
