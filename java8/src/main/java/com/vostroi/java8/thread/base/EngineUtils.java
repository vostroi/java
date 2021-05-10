package com.vostroi.java8.thread.base;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Administrator
 * @date 2021/3/16 11:52
 * @projectName java8
 * @title: EngineUtils
 * @description: 搜索工具类。模拟搜索引擎
 */
@Data
@Slf4j
public class EngineUtils {
    /**
     * 搜索引擎列表
     */
    private static List<String> engineList ;
    public static List<String> getEnineList(){
        return EngineUtils.engineList;
    };
    static{
        engineList = new ArrayList<>();
        engineList.add("BaiDu");
        engineList.add("Google");
        engineList.add("Bing");
        engineList.add("Sougou");
        engineList.add("Redis");
        engineList.add("Solr");
    }

    /**
     * 模拟搜索引擎的一次搜索
     * @param keywords
     * @param engine
     * @return
     */
    public static  String seach(String keywords , String engine){
        // 获取随机时间间隔 模拟搜索耗时
        int interval = new Random().nextInt(500);
        log.info("搜索引擎【{}】正在查询，预计耗时{}毫秒", engine, interval);
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "通过搜索引擎【" + engine +"】查询到问题【" + keywords + "】结果，耗时【" + interval + "】毫秒";
    }




}
