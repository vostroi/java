package com.vostroi.java8.features.stream;

import com.sun.jndi.cosnaming.CNCtx;
import com.sun.media.jfxmedia.logging.Logger;
import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.FREE_MEM;
import sun.applet.Main;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Administrator
 * @date 2021/2/19 10:58
 * @projectName java8
 * @title: DemoStream01
 * @description: 函数式编程：极大方便了处理集合类数据的效率；
 * stream 的功能包括 filter(过滤)，map(映射)，sort(排序)...数据集合经过 stream 处理后，转化为另一级集合 或 数据 输出
 */
@Slf4j
public class DemoStream01 {

    private static final String[] featuresArras = {"Stack","James", "Mary", "John", "Patricia", "Robert", "Anny", "Linda","patricia"};
    private static final List<String> featuresList = Arrays.asList(featuresArras);
    private static final Set<String> featuresSet = new HashSet<>(featuresList);


    /**
     * 创建Employee
     */
    private static Employee e1 = new Employee(1,23,"M","Rick","Beethovan");
    private static Employee e2 = new Employee(2,13,"F","Martina","Hengis");
    private static Employee e3 = new Employee(3,43,"M","Ricky","Martin");
    private static Employee e4 = new Employee(4,26,"M","Jon","Lowman");
    private static Employee e5 = new Employee(5,19,"F","Cristine","Maria");
    private static  Employee e6 = new Employee(6,15,"M","David","Feezor");
    private static  Employee e7 = new Employee(7,68,"F","Melissa","Roy");
    private static Employee e8 = new Employee(8,79,"M","Alex","Gussin");
    private static Employee e9 = new Employee(9,15,"F","Neetu","Singh");
    private static  Employee e10 = new Employee(10,45,"M","Naveen","Jain");
    private static  List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);

    /**
     * 用法1：替代 for 循环
     */
    public static void replaceFor() {
        List<String> list = featuresList
                // 将集合转换为管道流
                .stream()
                // 过滤 返回true的将被保留
                .filter(s -> s.startsWith("J"))
                //
                .map(String::toLowerCase)
                .sorted()
                // 收集
                .collect(Collectors.toList());
        System.out.println(list);
    }

    /**
     * 用法2：stream.of 将数组转化管道流
     * 将 List Set 转换为管道流
     */
    public static void trans2Stream1() {
        Stream<String> stream = Stream.of(DemoStream01.featuresArras);
        stream = Stream.of("Stack", "James", "Mary", "John", "Patricia", "Robert", "Anny", "Linda");
        stream = featuresSet.stream();
        stream = featuresList.stream();
    }

    /**
     * 用法3：将文本转换为管道流，逐行处理
     * Files.lines
     */
    public static void trans2Stream2() {
        Path path = null;
        try {
            path = Paths.get(DemoStream01.class.getClassLoader().getResource("application.properties").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            Stream<String> txtStream = Files.lines(path);
            List<String> list = txtStream.filter(l->l.contains(".")).collect(Collectors.toList());
            System.out.println(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * stream 的 filter 和谓语逻辑
     * 通常情况下，filter(其它方法一样)中的 lambda表达式是一次性使用的，不能复用；加入 Predicate 则可以达到复用目的
     * 如：DemoLambda01 中的 如何在 lambda 表达式中加入 Predicate
     */
    public static void predicateStream() {
        Predicate<Employee> ageLt60Predicate =  a -> a.getAge() > 60;
        Predicate<Employee> genderMPredicate = a -> a.getGender().equalsIgnoreCase("M");
        // 交集
        List<Employee> andList = employees.stream().filter(ageLt60Predicate.and(genderMPredicate)).collect(Collectors.toList());
        System.out.println(andList);
        // 并集
        List<Employee> orList = employees.stream().filter(ageLt60Predicate.or(genderMPredicate)).collect(Collectors.toList());
        System.out.println(orList);
        // 取反
        List<Employee> negateList = employees.stream().filter(ageLt60Predicate.or(genderMPredicate).negate()).collect(Collectors.toList());
        System.out.println(negateList);

    }

    /**
     * stream 的 map 针对管道中每个元素进行转换操作
     */
    public static void mapStream() {
        List<String> mapList = featuresList.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(mapList);

        List<Integer> mapStrLength = featuresList.stream().map(String::length).collect(Collectors.toList());
        List<String> peekStrLength = featuresList.stream().peek(str -> str.length()).collect(Collectors.toList());
        log.info("mapStrLength={}",mapStrLength);
        log.info("peekStrLength={}",peekStrLength);

        featuresList.stream().mapToInt(String::length).forEach(System.out::println);
        // peek 是一种特殊的 map， 当函数没有返回值 或 参数就是返回值时使用
        List<Employee> peekList = employees.stream().peek(e -> {
            e.setAge(e.getAge()+1);
            e.setGender(e.getGender().toUpperCase());
        }).collect(Collectors.toList());
        System.out.println(peekList);

        // map 的返回值 就是 参数 e
        List<Employee> mapList2 = employees.stream().map(e -> {
            e.setAge(e.getAge() + 2);
            e.setGender(e.getGender().toLowerCase());
            return e;
        }).collect(Collectors.toList());
        System.out.println(mapList2);
    }

    /**
     * 管道中还有管道情况（双重for循环情况，如二维数组，二维集合）
     * map 无法实现，map只针对一维数组，集合
     * flatMap 可以理解为将所有子管道中的元素全展示到父管道中处理
     */
    public static void flatMapStream(){
        featuresList.stream().flatMap(f -> Arrays.stream(f.split(""))).forEach(System.out::println);
    }

    /**
     * 管道数据截取 limit skip
     */
    public static void cutStream() {
        System.out.println(featuresList);
        // limit 截取管道中前N个元素
        List<String> limitList = featuresList.stream().limit(2).collect(Collectors.toList());
        // skip 跳过管道中的前N个元素，保留其余元素
        List<String> skipList = featuresList.stream().skip(2).collect(Collectors.toList());
        // distinct 去重（使用equals）
        List<String> distinctList = featuresList.stream().distinct().collect(Collectors.toList());
        // sort 默认按字母顺序排序 大写在小写前
        List<String> sortList = featuresList.stream().sorted().collect(Collectors.toList());

        System.out.println(limitList);
        System.out.println(skipList);
        System.out.println(distinctList);
        System.out.println(sortList);
    }

    /**
     * 串行，并行，顺序
     * 并行操作下，需要关注状态
     * 状态：
     *  状态通常代表公用数据，有状态就是有“公用数据”
     *  因为有公用的数据，状态通常需要额外的存储。
     *  状态通常被多人、多用户、多线程、多次操作，这就涉及到状态的管理及变更操作。
     *  并行（parallel）适用的场景
     *      1、数据源易拆分，parallel 更适用于ArrayList（基于数据，可以很容易根据索引拆分）而不是LinkedList
     *      2、适用于无状态操作，管道中每个元素的操作都互不影响
     *      3、基础数据源无变化，如文本（容易未知）读取就 不适合 使用parallel，适用于一开始容量固定的集合，方便平均拆分，同步处理
     */
    public static void serialParallel() {
        featuresList.stream().forEach(System.out::println);
        System.out.println(featuresList.stream().isParallel());
        // 并行下，顺序是不定的
        featuresList.stream().parallel().forEach(System.out::println);
    }

    /**
     * 性能测试
     */
    public static void performanceTest() {
        long insert = System.currentTimeMillis();
        int num = 80000000;
        Integer[] list = new Integer[num];
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            list[i]=random.nextInt();
        }

        log.info("total {} numbers insert time cost={}",num,System.currentTimeMillis()-insert);

        long start = System.currentTimeMillis();
        int min = list[0];
        for (int item : list) {
            if (item < min) {
                min = item;
            }
        }
        long time1 = System.currentTimeMillis();
        log.info("total {} numbers for find min num={},time cost={}",num,min,time1-start);

        min = Stream.of(list).mapToInt(x -> x).summaryStatistics().getMin();

        long time2 = System.currentTimeMillis();
        log.info("total {} stream find min num={},time cost={}",num,min,time2-time1);

        // 开启并行，发挥多核，性能提升
        min = Stream.of(list).parallel().mapToInt(x -> x).summaryStatistics().getMin();

        long time3 = System.currentTimeMillis();
        log.info("total {} stream parallel find min num={},time cost={}",num,min,time3-time2);
    }

    /**
     * 排序
     */
    public static void orderedStream() {
        // 大小写不敏感
        featuresList.sort(String.CASE_INSENSITIVE_ORDER);
        System.out.println(featuresList);
        // 字母表顺序
        featuresList.sort(Comparator.naturalOrder());
        System.out.println(featuresList);
        // 字母表顺序
        featuresList.sort(Comparator.reverseOrder());
        System.out.println(featuresList);

        // 复杂对象排序
        employees.sort(Comparator.comparing(e->e.getAge()));
        employees.sort(Comparator.comparing(Employee::getAge).reversed());
        System.out.println(employees);
        /**
         * 复杂对象多字段排序 像SQL一样排序
         * 全部正序：不加 reversed
         * 全部倒序：最后加 reversed
         * 如果需求是 一正一倒多字段交叉，此情况类似 负负得正情况，即后一个倒序会将前面的所有排序全部倒过来
         */
        employees.sort(Comparator.comparing(Employee::getGender).reversed().thenComparing(Employee::getAge));
        employees.forEach(System.out::println);
    }

    /**
     * 自定义排序 comparator
     */
    public static void customOrder() {
        /**
         * 匿名内部类方式实现
         * compare 返回 -1 0 1 前元素 小于 等于  大于 后元素（正序，反之则倒序）
         */
        employees.sort(new Comparator<Employee>() {

            @Override
            public int compare(Employee em1, Employee em2) {
                if(em1.getAge().intValue()==em2.getAge().intValue()){
                    return 0;
                }
                return em1.getAge().intValue() > em2.getAge().intValue() ? 1 : -1 ;
            }
        });


        // lambda实现
        employees.sort((em1,em2)->{
            return em1.getAge().intValue() == em2.getAge().intValue() ? 0 : em1.getAge().intValue() > em2.getAge().intValue() ? 1 : -1 ;
        });
        employees.forEach(System.out::println);
    }

    /**
     * 查找与匹配元素
     */
    public static void matchStream() {
        /**
         * anyMatch 是否存在 age>60 的元素
         * allMatch 是否全匹配
         * noneMatch 是否全不匹配
         */
        System.out.println(employees.stream().anyMatch(e -> e.getAge() > 60));
        System.out.println(employees.stream().allMatch(e -> e.getAge() > 10));
        System.out.println(employees.stream().noneMatch(e -> e.getAge() > 100));

        /**
         * 查找第一个匹配的元素
         * findFirst 查找第一个匹配的元素
         * findAny 查找任意一个匹配
         * Optional类代表一个值存在或不存在
         *  isPresent() 包含值 返回 true 否则 false
         *  ifPresent(Consumer)  如果存在 则执行代码
         *  orElse 存在时返回包含的值，否则返回默认值
         */
        Optional<Employee> first = employees.stream().filter(e -> e.getAge() > 50).findFirst();
        System.out.println(first.orElse(null));
        first.ifPresent(System.out::println);

    }

    /**
     * 集合归约
     * reduce(identity, accumulator, combiner)
     * identity 标识: 它是一个元素，归约操作的初始值，如果流为空，则为默认结果
     * accumulator 累加器：具有两个参数的函数，分别是 归约运算的部分结果 和 下个元素
     * combiner 合并器：可选，当归约并行化时，或当累加器参数的类型与累加器实现的类型不匹配时，用于合并归约操作的部分结果的函数
     */
    public static void reducedStream() {
        // Integer 类型归约 ， 初始值为0 ，累加器 accumulator 可以是lambda表达式，也可以是方法引用
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        Integer reduce = numbers.stream().reduce(0, (st, ne) -> st + ne);
        reduce = numbers.stream().reduce(0,(Integer::sum));
        System.out.println(reduce);

        // String 类型归约 ， 初始值为""
        String reduceStr = featuresList.stream().reduce("", String::concat);
        System.out.println(reduceStr);
        // 瞎写的
        String strLength = featuresList.stream().limit(40).reduce("", (ctnt, ne) -> {
            return Integer.sum(ctnt==""?ctnt.length():Integer.valueOf(ctnt), ne.length()) + "";
        });
        System.out.println(strLength);

        // 复杂对象 归约 先 map 再 reduce
        Integer sumAge = employees.stream().map(e -> e.getAge()).reduce(0, Integer::sum);
        System.out.println(sumAge);

        /**
         * 合并器 combiner 使用
         * 除了使用 map 处理复杂对象的集合归约，还可以用 combiner 来实现
         * 这里 流元素是 Employee 类型， 累加器 参数 是 Integer ，二者类型不匹配 需要使用 combiner 对累加器的结果进行二次归约，相当于做了类型转换
         */
        employees.stream().reduce(0, (totalAge , em)-> totalAge + em.getAge() , Integer::sum);

        // 并行流计算，可能会将集合分成多个组
        System.out.println("stream-->parallel-->reduce(,,)-->" + employees.stream().parallel().reduce(0, (totalAge, em) -> totalAge + em.getAge(), Integer::sum));
        System.out.println("parallelStream-->map-->reduce(,)-->" + employees.parallelStream().map(em -> em.getAge()).reduce(0, Integer::sum));
        // 为了更快将分组计算结果累加，可以使用合并器
        System.out.println("parallelStream-->map-->reduce(,,)-->" +employees.parallelStream().map(em -> em.getAge()).reduce(0, Integer::sum, Integer::sum));
    }

    /**
     * 对管理流的处理结果 操作
     */
    public static void resultStream() {
        // forEach 采用并行parallel 对元素处理的顺序无法保证
        featuresList.stream().parallel().sorted().forEach(System.out::println);
        // forEachOrdered 采用并行parallel 同样无法保证元素的处理顺序，但可以保证元素的输出顺序
        featuresList.stream().parallel().sorted().forEachOrdered(System.out::println);

        // collect 使用Collectors 的 方法  toSet 会去重，toList
        System.out.println(featuresList.stream().parallel().filter(f -> f.length() > 3).collect(Collectors.toSet()));
        // 更通用的收集方式  使用各种集合的构造函数 PriorityQueue::new LinkedHashSet::new...
        LinkedList<String> collect = featuresList.stream().parallel().filter(f -> f.length() > 4).collect(Collectors.toCollection(LinkedList::new));
        // 收集到数组Array  toArray() 不指定参数类型 默认是Object[]
        featuresList.stream().parallel().filter(f -> f.length() > 4).toArray(String[]::new);
        // 收集到 map
        Map<String, Integer> map1 = featuresList.stream().parallel().filter(f -> f.length() > 2).collect(Collectors.
                toMap(
                        // map 需要 key,这里使用 Function.identity() 意思是 输入元素就是输出元素 （等同于 x -> x 也就是输入就是输出的lambda表达式）,需要注意确保 key 的唯一性
//                        Function.identity(),
                        x -> x,
                        // 取 元素长度作为value
                        f -> f.length()
                )
        );

        // 分组收集 分组统计
        Map<Character, List<String>> mapList = featuresList.stream().parallel().filter(f -> f.length() > 1).collect(Collectors.groupingBy(x -> x.charAt(0)));
        // groupingBy(classifier, downstream) classifier 分组条件，downstream 收集器
        Map<Character, Long> map2 = featuresList.stream().parallel().filter(f -> f.length() > 1).collect(Collectors.groupingBy(x -> x.charAt(0), Collectors.counting()));
        log.info("mapList={},map2={}",mapList,map2);
    }

    /**
     *  其他常用方法
     */
    public static void otherMethod() {
        // 判断是否包含 注意 f->f
        featuresList.stream().anyMatch(f -> f.equals("John"));
        // 管道中 总数
        featuresList.stream().count();
        // 管道元素求和
        IntStream.of(5,6,7,8).sum();
        // 管道元素求平均值
        IntStream.of(5, 6, 7, 8).average();
        // 求最大值
        IntStream.of(5, 6, 7, 8).max().orElse(-1);
        // 管道元素的基本统计结果 min max avg count
        IntSummaryStatistics intSummaryStatistics = IntStream.of(5, 6, 7, 8, 9).summaryStatistics();
    }

    /**
     * map 排序 返回 LinkedHashMap 是有序的
     * 可以按键  按值进行排序
     */
    public static void mapSort() {
        int i=1;
        // 这里没处理 collectors.toMap key重复的情况
        Map<Integer, String> map = featuresList.stream().collect(Collectors.toMap(x -> x.length() + new Random().nextInt(100) , f -> f));
        map.merge(4, "TEST MERGE VALUE", new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) {
                return s + s2;
            }
        });
        // merge 方法  参数  参数1：key 参数2：value 参数3：当key重复时的处理逻辑 ov 原来该KEY的VALUE  , nv 参数2
        map.merge(4, "MERGE VALUE REPEAT", (ov, nv) -> ov + " && " + nv);
        System.out.println(map);

        // 按KEY排序 转成LinkedHashMap
        Map<Integer, String> mapSorted = map.entrySet().stream()
                // comparingByKey 搂 KEY 排序，  comparingByValue 按 VALUE 排序  可以传参 指定排序规则
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        // 与merge的参数3一致
                        (ov, nv) -> ov + " && " + nv,
                        LinkedHashMap::new));
        mapSorted.entrySet().stream().forEach(System.out::println);

        // 还可以利用 有序map  TreeMap 来排序
        TreeMap treeMap = new TreeMap(map);
        treeMap.entrySet().forEach(System.out::println);

    }

    public static void main(String[] args) {
//        replaceFor();
//        trans2Stream1();
//        trans2Stream2();
//        predicateStream();
//        mapStream();
//        flatMapStream();
//        cutStream();
//        serialParallel();
//        performanceTest();
//        orderedStream();
//        customOrder();
//        matchStream();
//        reducedStream();
//        resultStream();
        mapSort();

    }
}
