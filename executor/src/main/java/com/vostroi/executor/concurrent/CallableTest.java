package com.vostroi.executor.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author Administrator
 * @date 2021/5/10 11:39
 * @projectName executor
 * @title: CallableTest
 * @description: 任务，Executor的工作单元
 */
@Slf4j
public class CallableTest implements Callable {
    @Override
    public Object call() throws Exception {
        String content = "CallableTest call do something...";
        log.info(content);
        return content;
    }
}
