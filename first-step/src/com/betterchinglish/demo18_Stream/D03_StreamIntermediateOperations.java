package com.betterchinglish.demo18_Stream;

import java.util.*;
import java.util.stream.Stream;

/**
 * 模块三：Stream 流的核心中间方法（Intermediate Operations）
 * ----------------------------------------------------------------------
 * 【对应课程】：Stream流-03-Stream流的中间方法
 * 【中间操作核心特征】：
 *   1. 链式组装：每个中间操作都会返回一个【全新的 Stream 对象】，允许连续链式调用。
 *   2. 惰性求值（Lazy Evaluation）：所有中间操作仅作为流水线上的操作描述，在没有【终结操作】触发时，绝对不会真正执行！
 *
 * 【核心 API 详解】：
 *   - filter(Predicate<T>): 按照指定布尔条件过滤元素
 *   - limit(long maxSize): 截取流中前 maxSize 个元素
 *   - skip(long n): 跳过前 n 个元素（与 limit 结合可实现纯内存数据分页）
 *   - distinct(): 依赖对象的 hashCode() 和 equals() 进行去重
 *   - concat(Stream a, Stream b): 合并两个流
 *   - map(Function<T, R>): 映射转换，将元素从一种类型转换为另一种类型（1 对 1）
 *   - flatMap(Function<T, Stream<R>>): 扁平化映射，将每个元素转为一个子流后摊平合并（1 对多）
 *   - sorted() / sorted(Comparator<T>): 自然排序与指定比较器排序
 *   - peek(Consumer<T>): 窥探/调试中间流经的元素，不改变元素流
 */
public class D03_StreamIntermediateOperations {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块三】Stream 流的核心中间操作详解");
        System.out.println("==================================================");

        /* ---------------------------------------------------------
         * 1. filter(Predicate) 条件过滤
         * --------------------------------------------------------- */
        System.out.println("--- 1. filter 过滤 ---");
        List<String> names = List.of("张三丰", "张无忌", "周芷若", "赵敏", "张翠山", "张良");
        System.out.print("筛选姓张且名字>=3个字: ");
        names.stream()
                .filter(s -> s.startsWith("张"))
                .filter(s -> s.length() >= 3)
                .forEach(s -> System.out.print(s + " "));
        System.out.println();

        /* ---------------------------------------------------------
         * 2. limit 与 skip（经典内存分页算法）
         * --------------------------------------------------------- */
        System.out.println("\n--- 2. limit 截取与 skip 跳过（分页） ---");
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.print("limit(3) 获取前3个: ");
        numbers.stream().limit(3).forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.print("skip(3) 跳过前3个: ");
        numbers.stream().skip(3).forEach(n -> System.out.print(n + " "));
        System.out.println();

        // 模拟分页：每页 3 条数据，获取第 2 页 (页码 page=2, 每页大小 pageSize=3)
        // 跳过条数 = (page - 1) * pageSize = 3，截取条数 = pageSize = 3
        int page = 2, pageSize = 3;
        System.out.print("分页模拟 (第 " + page + " 页, 大小 " + pageSize + "): ");
        numbers.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .forEach(n -> System.out.print(n + " "));
        System.out.println();

        /* ---------------------------------------------------------
         * 3. distinct 去重
         * --------------------------------------------------------- */
        System.out.println("\n--- 3. distinct 去重 ---");
        List<String> duplicates = List.of("Java", "Python", "Java", "Go", "Python", "Rust");
        System.out.print("去重前: " + duplicates + " -> 去重后: ");
        duplicates.stream().distinct().forEach(s -> System.out.print(s + " "));
        System.out.println();

        /* ---------------------------------------------------------
         * 4. Stream.concat 流合并
         * --------------------------------------------------------- */
        System.out.println("\n--- 4. Stream.concat 合并两个流 ---");
        Stream<String> stream1 = Stream.of("苹果", "香蕉");
        Stream<String> stream2 = Stream.of("西瓜", "橙子");
        System.out.print("合并流输出: ");
        Stream.concat(stream1, stream2).forEach(s -> System.out.print(s + " "));
        System.out.println();

        /* ---------------------------------------------------------
         * 5. map 映射转换 (1 对 1)
         * --------------------------------------------------------- */
        System.out.println("\n--- 5. map 映射转换 ---");
        List<String> rawScores = List.of("10", "20", "30", "40");
        System.out.print("字符串列表映射为数字并乘以2: ");
        rawScores.stream()
                .map(Integer::parseInt) // String -> Integer
                .map(n -> n * 2)        // 每个元素 * 2
                .forEach(n -> System.out.print(n + " "));
        System.out.println();

        /* ---------------------------------------------------------
         * 6. flatMap 扁平化映射 (1 对 多)
         * --------------------------------------------------------- */
        System.out.println("\n--- 6. flatMap 扁平化映射 ---");
        List<String> sentences = List.of("hello world", "java stream api");
        System.out.print("句子分词并摊平为单词流: ");
        sentences.stream()
                .flatMap(line -> Arrays.stream(line.split(" "))) // 每个句子拆解为单词流，平铺到外层
                .forEach(word -> System.out.print("[" + word + "] "));
        System.out.println();

        /* ---------------------------------------------------------
         * 7. sorted 自然排序与自定义比较器排序
         * --------------------------------------------------------- */
        System.out.println("\n--- 7. sorted 排序操作 ---");
        List<Integer> unorderNums = List.of(15, 3, 28, 9, 42, 6);

        System.out.print("自然升序: ");
        unorderNums.stream().sorted().forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.print("自定义降序: ");
        unorderNums.stream().sorted((a, b) -> b - a).forEach(n -> System.out.print(n + " "));
        System.out.println();

        /* ---------------------------------------------------------
         * 8. 惰性求值（Lazy Evaluation）实验与 peek 窥探
         * --------------------------------------------------------- */
        System.out.println("\n--- 8. 惰性求值本质实验（重点） ---");
        System.out.println("实验 A：只链式调用中间方法 filter / peek，但不调用任何终结方法：");
        List<String> testList = List.of("A", "B", "C");
        // 注意：下面这行由于没有终结方法，控制台绝对没有任何 peek 打印！
        Stream<String> unexecutedStream = testList.stream()
                .peek(item -> System.out.println("--> 中间处理了: " + item));
        System.out.println("【观察】：没有任何输出！证明中间操作是完全惰性的，只是注册了算子任务。");

        System.out.println("实验 B：加上终结方法 count() 触发流水线执行：");
        long count = unexecutedStream.count();
        System.out.println("触发终结操作后，peek 打印执行完毕，最终元素个数: " + count + "\n");
    }
}
