package com.vostroi.socket.dubbo.simulate;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Administrator
 * @date 2021/12/22 16:21
 * @projectName java8
 * @title: RPC
 * @description: 模拟 RPC 框架  实际是socket服务端
 */
@Slf4j
public class RPC {


    /**
     * 暴露 服务
     * @param service  要暴露的 service
     * @param port 端口
     */
    public static void exportServer(Object service, int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            log.info("ServerSocket server...");

            // 死循环 接收socket 连接
            while (true) {
                log.info("服务端等待连接...");
                Socket socket = server.accept();
                log.info("连接成功...{}" , socket);

                new Thread(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        InputStream inputStream = socket.getInputStream();

                        ObjectInputStream ois = new ObjectInputStream(inputStream);

                        String methodName = (String) ois.readObject();

                        Class<?>[] o1 = (Class<?>[]) ois.readObject();

                        Object[] o2 = (Object[]) ois.readObject();

                        Method method = service.getClass().getMethod(methodName + "", o1);
                        Object result = method.invoke(service, o2);

                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(result);

                    }
                }).start();



            }


        } catch (IOException e) {
            log.error("RPC 错误：", e);
        }
    }


    // 调用者使用 ， 获取 代理 来调用  provider的 接口
    public static <T> T refer(Class<T> interfaceClass, String host, int port) {
        Object proxy =  Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 连接 socket
                Socket socket = new Socket(host, port);

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                oos.writeObject(method.getName());

                oos.writeObject(method.getParameterTypes());

                oos.writeObject(args);

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Object result = ois.readObject();

                return result;
            }
        });

        return (T) proxy;
    }



}
