package com.betterchinglish.demo18_Stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 模块五：收集方法 collect 超详解（Collectors 核心大全）
 * ----------------------------------------------------------------------
 * 【对应课程】：Stream流-05-收集方法collect超详解
 * 【核心定位】：
 *   Stream 流处理完数据后，最常见的业务诉求不是在控制台 forEach 打印，而是将清洗好的数据【重新装入集合或 Map】中，
 *   继续流转到业务层（Service / DAO / Web 控制器）处理。`collect(Collector)` 就是完成该转换的核心枢纽。
 *
 * 【核心 API 详解】：
 *   1. 收集到 List：`collect(Collectors.toList())` 或 Java 16+ `stream.toList()`
 *   2. 收集到 Set：`collect(Collectors.toSet())`（自动去重）
 *   3. 收集到自定义容器：`collect(Collectors.toCollection(TreeSet::new))`
 *   4. 收集到 Map：`collect(Collectors.toMap(keyMapper, valueMapper, mergeRule, mapSupplier))`
 *      - 掌握【Key 冲突】的规避方案；
 *      - 掌握保证键值有序的 `LinkedHashMap` 注入技巧。
 *   5. 高级分组与分区：
 *      - `Collectors.groupingBy()` 单级/多级分组、下游聚合统计
 *      - `Collectors.partitioningBy()` 布尔二分
 *   6. 字符串拼装：`Collectors.joining()`
 */
public class D05_StreamCollectOperations {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块五】收集方法 collect 超详解与 Collectors 实战");
        System.out.println("==================================================");

        List<String> list = List.of("张三-23-男", "李四-24-女", "王五-25-男", "赵六-23-女", "张三-23-男");

        /* ---------------------------------------------------------
         * 1. 收集到 List 与 Set
         * --------------------------------------------------------- */
        System.out.println("--- 1. 收集到 List 与 Set ---");
        // 收集为 List
        List<String> collectedList = list.stream()
                .filter(s -> s.startsWith("张"))
                .collect(Collectors.toList());
        System.out.println("收集为 List: " + collectedList);

        // 收集为 Set（自动消除重复项）
        Set<String> collectedSet = list.stream()
                .collect(Collectors.toSet());
        System.out.println("收集为 Set (自动去重): " + collectedSet);

        // Java 16+ 极简语法：stream.toList()（注意：返回的是不可变只读 List）
        List<String> unmodifiableList = list.stream().toList();
        System.out.println("Java 16+ stream.toList(): " + unmodifiableList);

        /* ---------------------------------------------------------
         * 2. 收集为 Map 及解决【重复 Key 冲突】
         * --------------------------------------------------------- */
        System.out.println("\n--- 2. 收集为 Map 与 Key 冲突处理 ---");
        // 提取姓名作为 Key，年龄作为 Value
        // 注意：原数据中有两个 "张三-23-男"，如果不加合并函数直接 toMap，会抛出 IllegalStateException: Duplicate key！

        // 基础版本（带冲突解决合并函数）：(existing, replacement) -> existing 保留前者
        Map<String, Integer> nameAgeMap = list.stream()
                .distinct() // 先去重，或在 toMap 时处理冲突
                .collect(Collectors.toMap(
                        item -> item.split("-")[0],            // Key 映射
                        item -> Integer.parseInt(item.split("-")[1]), // Value 映射
                        (oldVal, newVal) -> newVal              // 冲突策略：若遇到重复 Key，以新值覆盖旧值
                ));
        System.out.println("收集为普通 Map: " + nameAgeMap);

        // 高级版本：指定 Map 具体实现类为 LinkedHashMap，保留插入顺序
        LinkedHashMap<String, Integer> orderedMap = list.stream()
                .collect(Collectors.toMap(
                        item -> item.split("-")[0],
                        item -> Integer.parseInt(item.split("-")[1]),
                        (v1, v2) -> v1,                       // 重复时保留前者
                        LinkedHashMap::new                    // 指定实现类
                ));
        System.out.println("指定 LinkedHashMap 实现: " + orderedMap);

        /* ---------------------------------------------------------
         * 3. groupingBy 分组与下游统计
         * --------------------------------------------------------- */
        System.out.println("\n--- 3. groupingBy 分组与统计 ---");
        // 需求：按性别分组 ("男" vs "女")
        Map<String, List<String>> groupMap = list.stream().collect(
                Collectors.groupingBy(item -> item.split("-")[2])
        );
        System.out.println("按性别分组结果: " + groupMap);

        // 需求：按性别分组，统计各性别的人数（下游收集器 Collectors.counting()）
        Map<String, Long> countByGender = list.stream().collect(
                Collectors.groupingBy(
                        item -> item.split("-")[2],
                        Collectors.counting()
                )
        );
        System.out.println("按性别统计人数: " + countByGender);

        /* ---------------------------------------------------------
         * 4. partitioningBy 分区（布尔二分）
         * --------------------------------------------------------- */
        System.out.println("\n--- 4. partitioningBy 分区 ---");
        // 需求：按年龄是否 >= 24 岁分为两个区 (true/false)
        Map<Boolean, List<String>> partitionMap = list.stream().collect(
                Collectors.partitioningBy(item -> Integer.parseInt(item.split("-")[1]) >= 24)
        );
        System.out.println(">= 24 岁分区 (true): " + partitionMap.get(true));
        System.out.println("<  24 岁分区 (false): " + partitionMap.get(false));

        /* ---------------------------------------------------------
         * 5. joining 字符串连接
         * --------------------------------------------------------- */
        System.out.println("\n--- 5. joining 字符串拼接 ---");
        List<String> tags = List.of("Java", "Spring", "Redis", "MySQL");
        // 指定分隔符、前缀、后缀
        String joined = tags.stream().collect(Collectors.joining(" -> ", "[", "]"));
        System.out.println("joining 格式化输出: " + joined + "\n");
    }
}
