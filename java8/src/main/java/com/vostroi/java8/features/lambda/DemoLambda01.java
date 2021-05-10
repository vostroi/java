package com.vostroi.java8.features.lambda;

import com.vostroi.java8.beans.Customer;
import com.vostroi.java8.inters.ArrayFactory;
import com.vostroi.java8.inters.CustomerFactory;
import com.vostroi.java8.inters.NumberHelper;
import com.vostroi.java8.inters.Printer;
import com.vostroi.java8.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2021/2/7 9:58
 * @projectName java8
 * @title: DemoLambda01
 * @description:
 * lambda表达式，表达的是接口函数。lambda无法单独出现，需要函数式接口（@FunctionalInterface)来存放，lambda方法体实际就是函数式接口的具体实现
 * JDK中函数式接口 java.lang.Runnable，java.util.Comparator，java.util.concurrent.Callable，java.util.function.*如Consumer、Predicate、Supplier
 *
 * 语法：(param1, param2, param3...) -> {代码块}
 * ->左边是函数形参列表
 * ->右边 方法体，可以是表达式和代码块，方法体函数式接口里面方法的实现，如果是代码块，则必须用{}来包裹起来，且需要一个return 返回值，但有个例外，若函数式接口里面方法返回值是void，则无需{}
 * lambda表达式可正式替代匿名内部类，有一些注意
 * lambda中的this，指的是包围lambda表达式的类；而匿名内部类的this指的是匿名内部类
 * Java编译器将lambda统计为私有方法，使用了Java 7的 InvokeDynamic 字节码指令来动态绑定这个方法。
 * https://www.cnblogs.com/lucky_dai/p/5478753.html   lambda 的10个示例
 */
@Slf4j
public class DemoLambda01 {

    /**
     * 方法要定义行为逻辑
     * @param content
     */
    public void printSth(String content) {
        // do something
        log.info("printSth===>{}",content);
    }

    /**
     * 方法不再定义行为逻辑，只调用接口的行为逻辑
     * @param content
     * @param printer
     */
    public void printSth(String content, Printer printer) {
        // do something
        printer.print(content);
    }

    /**
     * 最经典的POJO实现
     * 通过创建对象，调用函数实现
     */
    public static void normal_01(){
        long time0 = System.currentTimeMillis();

        DemoLambda01 demo = new DemoLambda01();
        String content = "Hello 2021.";
        demo.printSth(content);

        log.info("time cost millis :{}",System.currentTimeMillis()-time0);
    }

    /**
     * 传统的接口函数实现-但需要实现接口的所有方法
     */
    public static void normal_02() {
        long time0 = System.currentTimeMillis();

        DemoLambda01 demo = new DemoLambda01();
        String content = "Hello 2021";
        Printer printer = new Printer() {
            @Override
            public void print(String content) {
                log.info("printSth===>normal_02===>printer===>print===>{}", content);
            }
        };
        demo.printSth(content, printer);

        log.info("time cost millis :{}",System.currentTimeMillis()-time0);
    }

    /////////////////////////////////////////////////////////////////////////////////// lambda 的 多种用法///////////////////////////////////////////////////////////////////////////////////
    /**
     * lambda 表达式的使用前提是， 使用函数式接口来使用 @FunctionalInterface
     * 比如 函数式接口作方法参数；函数式接口作方法返回值；函数式接口创建实现类对象 ...
     * lambda表达式的延迟执行
     *      使用lambda原因：将代码的执行延迟到一个合适的时间点。所有的Lambda表达式都是延迟执行的
     *      有些场景的代码执行后，结果不一定会被使用，从而造成性能浪费。而lambda表达式是延迟执行的，这正好作为解决方案，提升性能
     *
     */
    /**
     * 用法1：函数式接口
     * 此例：函数式接口作为方法的参数
     */
    public static void lambda_01() {
        long time0 = System.currentTimeMillis();

        DemoLambda01 demo = new DemoLambda01();
        String content = "Hello 2021";
        // 函数式接口创建实现类对象
        //Printer printer = (String ctnt) -> {log.info("printSth===>lambda_01===>printer===>print===>{}",ctnt);};
        // 简化：去掉参数类型
        //Printer printer = (ctnt) -> {log.info("printSth===>lambda_01===>printer===>print===>{}",ctnt);};
        // 简化：去掉括号
        Printer printer = ctnt -> log.info("printSth===>lambda_01===>printer===>print===>{}",ctnt);
        // 如果方法没有参数，左边用括号代替 () -> {log.info("....");};
        demo.printSth(content, printer);

        log.info("time cost millis :{}",System.currentTimeMillis()-time0);
    }

    /**
     * 用法1：函数式接口
     * 此例：函数式接口作为方法的返回值
     */
    public static void print(String code) {
        // 自定义业务
        // code ...
       log.info("getInstance===>printer===>print===>{}",code);
    }

    /**
     * 用法2：方法引用，lambda表达式的一种简写，所引用的方法是lambda表达式的方法体实现
     * 原理：也就是被引用的方法要实现函数接口的抽象方法的功能
     *      1.创建函数式接口的匿名内部类对象
     *      2.重写接口中的抽象方法，并在抽象方法中调用被引用的方法
     * Object(Reference) :: methodName
     * 其实是lambda的简化写法
     * ::左边是容器（可以是类名，实例名）
     * ::右边是相应的方法名，即所引用的方法，也就是lambda表达式右边方法体的内容
     * 如果是静态方法： Object :: static method
     *      1.被引用的静态方法参数列表必须与函数式接口中抽象方法参数一致
     *      2.函数式接口的抽象方法没有返回值，引用的静态方法可以有返回值，也可以没有返回值
     *      3.函数式接口的抽象方法有返回值，引用的静态方法返回值必须与抽象方法一致
     * 如果是实例方法： obj :: 非 static method
     *      1.被引用的方法参数列表必须与函数式接口中抽象方法参数一致
     *      2.函数式接口的抽象方法没有返回值，引用的方法可以有返回值，也可以没有返回值
     *      3.函数式接口的抽象方法有返回值，引用的方法返回值必须与抽象方法一致
     * 如果是构造函数： Object :: new
     *      1.被引用的类，必须存在一个构造方法的参数列表和函数式接口的抽象方法参数一致
     */
    public static void lambdaUse_01() {
        String content = " lambdaUse_01===>Hello 2021";
        /**
         * 静态方法引用：  函数式接口作为方法的参数
         */
        new DemoLambda01().printSth("函数式接口作为方法的参数" + content, System.out::println);

        /**
         * 静态方法引用：函数式接口作为方法返回值
         */
        Printer printer = DemoLambda01::print;
        printer.print("函数式接口作为方法返回值" + content);

        /**
         * 实例方法引用
         */
        NumberHelper numHelp = new CommonUtils()::randomA2B;
        log.info("实例方法引用 lambdaUse_01===>randomA2B===>{}",numHelp.randomA2B(12, 20));

        /**
         * 构造方法引用
         */
        CustomerFactory cf = Customer::new;
        log.info("构造方法引用 lambdaUse_01===>CustomerFactory===>new===>{}",cf.getInstance(30, 188));

        /**
         * 数组构造方法引用
         */
        ArrayFactory af = int[] :: new;
        log.info("数组构造方法引用 lambdaUse_01===>ArrayFactory===>new===>{}",af.build(8));

        /**
         * 特定类型实例方法引用（非static）
         *      1.被引用的方法要比函数式接口中的抽象方法 少 一个参数
         *      2.函数式接口中的抽象方法的参数列表的第一个参数是用来调用被引用的方法，抽象方法中的其它参数都作为被引用方法的参数传递
         */
        String[] arrs = {"AA", "James", "Mary", "John", "Patricia", "Robert", "aa", "Linda"};
        Arrays.sort(arrs , (a , b)->a.compareToIgnoreCase(b));
        // 等同于上一行代码
        Arrays.sort(arrs , String::compareToIgnoreCase);
        log.info("特定类型实例方法引用 lambdaUse_01===>Arrays.sort===>{}",arrs);
        for (String arr : arrs) {
            System.out.println(arr);
        }

        /**
         * 类中方法 调用父类 或 本类 方法引用
         * 在有继承关系的类中，若方法想调用本类或父类的成员方法
         * 在函数式接口抽象方法与本类（父类）成员方法参数列表相同，且返回值类型相同的情况下，使用
         * this :: method
         * super :: method
         */

        /**
         * 用法3：对列表进行迭代
         */
        List<String> features = Arrays.asList(arrs);
        features.forEach(str -> System.out.println(str));
        // 使用方法引用，更简洁
        features.forEach(System.out::println);

        /**
         * 用法4：结合函数式接口 Predicate 使用
         * Java增加了 java.util.function 包来支持函数式编程，其中一个便是 Predicate
         */
        filter(features, (str) -> str.toString().startsWith("J"));

        /**
         * 如何在 lambda 表达式中加入 Predicate
         * Predicate 允许将多个 Predicate 合并成一个，提供类型操作符 and 和 or 的方法： and(),or(),xor()
         * 多条件情况下，能让代码简洁
         */
        Predicate<String> startWithJ = (s) -> s.startsWith("J");
        Predicate<String> length4 = (s)->s.length()==4;
        features.stream().filter(startWithJ.and(length4)).forEach(str-> System.out.println("过滤列表中以J开头，且长度为4的字符串：" + str));
        // 通过过滤创建新的列表
        List<String> filteredList = features.stream().filter(startWithJ.and(length4)).collect(Collectors.toList());
        log.info("before filter {},after filter{}",features , filteredList);

        /**
         * 用法5：使用 lambda 的 Map 和 Reduce
         * 函数式编程概念：Map 允许将对象进行转换，
         */
        taxOrder();
        taxOrder2();

        /**
         * stream中还有很多有用方法 distinct()去重,summaryStatistics() 返回列表元素的各种摘要信息，最大 getMax，最小 getMin....
         */

        // 看到这了
//        https://blog.csdn.net/weixin_42022555/article/details/81943263
//        https://www.cnblogs.com/lucky_dai/p/5478753.html
    }
    /////////////////////////////////////////////////////////////////////////////////// lambda 的 多种用法///////////////////////////////////////////////////////////////////////////////////


    public static void main(String[] args) {

        /*
        normal_01(); // 耗时较少

        normal_02(); // 耗时最少

        lambda_01(); // 耗时最长
        */

        lambdaUse_01();
    }

    public static void filter(List list, Predicate predicate) {
        list.forEach(l->{
            if (predicate.test(l)) {
                System.out.println(l);
            }
        });
    }

    public static void filter2(List list, Predicate predicate) {
        list.stream().filter((l)->{
            return predicate.test(l);
        }).forEach((l)->{
            System.out.println(l);
        });
        // 将上面代码进一步简化
        list.stream().filter(l -> predicate.test(l)).forEach(System.out::println);
    }

    /**
     * 为每个订单增加17%的税
     */
    public static void taxOrder() {
        Double total = 0D;
        // 订单
        List<Integer> orders = Arrays.asList(100, 200, 300, 400, 500, 600);
        for (Integer order : orders) {
            double taxOrder = order + .17 * order;
            System.out.println(taxOrder);
            total = total + taxOrder;
        }
        log.info("taxOrder total={}", total);
    }

    /**
     * 为每个订单增加17%的税 使用 lambda 表达式
     */
    public static void taxOrder2() {
        // 订单
        List<Integer> orders = Arrays.asList(100, 200, 300, 400, 500, 600);
        // map 方法会将 ord + .17 * ord 代码应用到流中的每个元素
        orders.stream().map(ord -> ord + .17 * ord).forEach(System.out::println);

        //List<Double> taxOrders = orders.stream().map(ord -> ord * 1.17).collect(Collectors.toList());

        /**
         * reduce 函数可以将所有值合并成一个（又称为折叠操作）， map 和 reduce 操作是函数式编程的核心操作，流(strea )API定义的 reduce 可以接受 lambda表达式，并对所有值进行合并
         * 如 IntStream 的 count(),average() mapToLong(),mapToDouble()
         */
        Double total = orders.stream().map((ord -> ord * 1.17)).reduce((s, ord) -> s + ord).get();
    }

}
