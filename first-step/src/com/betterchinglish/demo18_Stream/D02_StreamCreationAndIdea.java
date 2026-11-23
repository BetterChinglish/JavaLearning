package com.betterchinglish.demo18_Stream;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 模块二：Stream 流的思想与获取 Stream 流的方式
 * ----------------------------------------------------------------------
 * 【对应课程】：Stream流-02-Stream流的思想和获取Stream流
 * 【核心思想】：
 *   1. 传送带模型：Stream 就像一条流水线传送带，把数据从源头搬运过来，经过一道道加工工序（过滤、映射、去重等），最后包装下线。
 *   2. 自身不存储数据：Stream 不是集合，不存储任何元素，只是在管道中计算数据。
 *   3. 不改变数据源：流的所有操作都不会修改原集合或原数组的内容。
 *   4. 一次性消费（不可复用）：流一旦经过终结操作，管道立即关闭，不能再次使用，否则抛出 IllegalStateException。
 *
 * 【获取方式总结】：
 *   1. 单列集合 (Collection)：直接调用 default 方法 `stream()` 或 `parallelStream()`。
 *   2. 双列集合 (Map)：不能直接获取，必须通过 `keySet().stream()`、`values().stream()` 或 `entrySet().stream()`。
 *   3. 数组 (Array)：
 *      - 推荐使用 `Arrays.stream(arr)`；
 *      - 或者 `Stream.of(arr)`（注意基本类型数组的经典深坑！）。
 *   4. 同种类型的零散数据：`Stream.of(T... values)`。
 *   5. 基本类型特化流：`IntStream`, `LongStream`, `DoubleStream`。
 */
public class D02_StreamCreationAndIdea {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块二】Stream 流的核心思想与多种获取方式");
        System.out.println("==================================================");

        /* ---------------------------------------------------------
         * 1. 单列集合获取 Stream 流（List, Set 等）
         * --------------------------------------------------------- */
        System.out.println("--- 1. 单列集合获取 Stream ---");
        List<String> list = List.of("Java", "Go", "Rust", "Python");
        Stream<String> listStream = list.stream();
        System.out.print("List 流遍历: ");
        listStream.forEach(s -> System.out.print(s + " "));
        System.out.println();

        Set<Integer> set = Set.of(10, 20, 30);
        System.out.print("Set 流遍历: ");
        set.stream().forEach(n -> System.out.print(n + " "));
        System.out.println();

        /* ---------------------------------------------------------
         * 2. 双列集合获取 Stream 流（Map 体系）
         * --------------------------------------------------------- */
        System.out.println("\n--- 2. 双列集合获取 Stream（键/值/键值对三种方式） ---");
        Map<String, Integer> scoreMap = Map.of("张三", 95, "李四", 88, "王五", 72);

        // 方式一：根据键流获取
        System.out.print("Map.keySet().stream(): ");
        scoreMap.keySet().stream().forEach(name -> System.out.print(name + " "));
        System.out.println();

        // 方式二：根据值流获取
        System.out.print("Map.values().stream(): ");
        scoreMap.values().stream().forEach(score -> System.out.print(score + " "));
        System.out.println();

        // 方式三：根据键值对 Entry 获取（最常用）
        System.out.print("Map.entrySet().stream(): ");
        scoreMap.entrySet().stream()
                .forEach(entry -> System.out.print("[" + entry.getKey() + " -> " + entry.getValue() + "] "));
        System.out.println();

        /* ---------------------------------------------------------
         * 3. 数组获取 Stream 流与【经典深坑剖析】
         * --------------------------------------------------------- */
        System.out.println("\n--- 3. 数组获取 Stream 与基本类型数组的大坑 ---");
        String[] strArr = {"Hello", "Stream", "World"};

        // 引用类型数组：Arrays.stream 与 Stream.of 效果一致
        System.out.print("引用类型数组 Arrays.stream: ");
        Arrays.stream(strArr).forEach(s -> System.out.print(s + " "));
        System.out.println();

        // 【高频面试避坑题】：基本类型数组的 Stream.of vs Arrays.stream
        int[] intArr = {1, 2, 3, 4, 5};

        // (A) 正确做法：Arrays.stream(intArr) 返回基本类型特化流 IntStream
        System.out.print("Arrays.stream(int[]): ");
        Arrays.stream(intArr).forEach(n -> System.out.print(n + " "));
        System.out.println();

        // (B) 易错踩坑：Stream.of(intArr)
        // 原因：Stream.of(T... values) 要求泛型 T 为引用类型！
        // 传递 int[] 时，Java 将整个 int[] 数组对象作为一个泛型元素 T，返回的是 Stream<int[]>，流的长度只有 1！
        Stream<int[]> trappedStream = Stream.of(intArr);
        System.out.print("Stream.of(int[]) 的实际元素个数与类型: ");
        System.out.println("元素个数 = " + trappedStream.count() + " (陷阱：整个数组被包装成了一个单独的对象！)");

        // 如果是包装类型 Integer[]，则 Stream.of(integerArr) 能够被正确解包
        Integer[] integerArr = {1, 2, 3};
        System.out.print("Stream.of(Integer[]): ");
        Stream.of(integerArr).forEach(n -> System.out.print(n + " "));
        System.out.println();

        /* ---------------------------------------------------------
         * 4. 零散数据获取 Stream 与基本特化流范围生成
         * --------------------------------------------------------- */
        System.out.println("\n--- 4. 零散数据与数值范围生成 ---");
        Stream.of("A", "B", "C").forEach(s -> System.out.print(s + " "));
        System.out.println();

        // IntStream.range(1, 5) 左闭右开 [1, 5)
        System.out.print("IntStream.range(1, 5): ");
        IntStream.range(1, 5).forEach(n -> System.out.print(n + " "));
        System.out.println();

        // IntStream.rangeClosed(1, 5) 左闭右闭 [1, 5]
        System.out.print("IntStream.rangeClosed(1, 5): ");
        IntStream.rangeClosed(1, 5).forEach(n -> System.out.print(n + " "));
        System.out.println();

        /* ---------------------------------------------------------
         * 5. Stream 流的一次性消费（不可复用）特性验证
         * --------------------------------------------------------- */
        System.out.println("\n--- 5. Stream 流的不可复用性（一次性消费）---");
        Stream<String> reusableStream = Stream.of("One", "Two", "Three");
        reusableStream.forEach(s -> {}); // 第一次终结操作，流水线正常关闭

        try {
            // 尝试对已关闭的流再次调用终结操作
            reusableStream.forEach(s -> {});
        } catch (IllegalStateException e) {
            System.out.println("第二次消费已关闭的流，成功捕获预期异常: " + e.getMessage());
            System.out.println("【结论】：流是一次性消费品，终结操作执行后流即废弃，若需再次使用必须重新构建新流！\n");
        }
    }
}
