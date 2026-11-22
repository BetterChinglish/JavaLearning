package com.betterchinglish.demo18_Stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 模块六：综合练习 1 - 数字过滤与聚合计算
 * ----------------------------------------------------------------------
 * 【对应课程】：Stream流-06-综合练习1-数字过滤
 * 【练习目标】：
 *   通过纯数字流水线场景，深度巩固 filter, map, sorted, limit, reduce 及基本类型特化流。
 *
 * 【需求清单】：
 *   现有整数列表：[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
 *   1. 练习一：过滤出所有偶数，并收集为新 List。
 *   2. 练习二：将所有偶数乘以 10，按降序排列，取前 3 个打印。
 *   3. 练习三：计算所有偶数的平方和（sum of squares）。
 *   4. 练习四：使用 IntSummaryStatistics 一键获取这批数字的统计摘要（最大值、最小值、总和、均值）。
 */
public class D06_PracticeNumberFilter {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块六】综合练习 1：数字过滤与聚合计算");
        System.out.println("==================================================");

        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println("原始整数列表: " + list);

        /* ---------------------------------------------------------
         * 练习 1：过滤偶数并收集
         * --------------------------------------------------------- */
        System.out.println("\n--- 练习 1：过滤偶数并收集为 List ---");
        List<Integer> evenList = list.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("过滤出的偶数列表: " + evenList);

        /* ---------------------------------------------------------
         * 练习 2：偶数乘以 10，降序排列，截取前 3 个
         * --------------------------------------------------------- */
        System.out.println("\n--- 练习 2：偶数乘以 10 -> 降序排列 -> 取前 3 个 ---");
        System.out.print("处理结果: ");
        list.stream()
                .filter(n -> n % 2 == 0)             // 只要偶数: 2, 4, 6, 8, 10
                .map(n -> n * 10)                    // 乘以10: 20, 40, 60, 80, 100
                .sorted((a, b) -> b - a)             // 降序排列: 100, 80, 60, 40, 20
                .limit(3)                            // 取前3个: 100, 80, 60
                .forEach(n -> System.out.print(n + " "));
        System.out.println();

        /* ---------------------------------------------------------
         * 练习 3：计算偶数的平方和
         * --------------------------------------------------------- */
        System.out.println("\n--- 练习 3：偶数的平方和 (2^2 + 4^2 + 6^2 + 8^2 + 10^2) ---");
        // 2*2=4, 4*4=16, 6*6=36, 8*8=64, 10*10=100 -> sum = 220
        int sumOfSquares = list.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .reduce(0, Integer::sum);
        System.out.println("平方和 (reduce 计算): " + sumOfSquares);

        /* ---------------------------------------------------------
         * 练习 4：使用 IntSummaryStatistics 一键生成统计摘要
         * --------------------------------------------------------- */
        System.out.println("\n--- 练习 4：IntSummaryStatistics 统计全家桶 ---");
        // mapToInt 转为基本类型特化流 IntStream，避免自动拆装箱，并提供 summaryStatistics()
        IntSummaryStatistics stats = list.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics();

        System.out.println("元素个数: " + stats.getCount());
        System.out.println("总和 (Sum): " + stats.getSum());
        System.out.println("最小值 (Min): " + stats.getMin());
        System.out.println("最大值 (Max): " + stats.getMax());
        System.out.println("平均值 (Average): " + stats.getAverage() + "\n");
    }
}
