package com.betterchinglish.demo18_Stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 模块七：综合练习 2 - 字符串过滤、清洗与收集
 * ----------------------------------------------------------------------
 * 【对应课程】：Stream流-07-综合练习2-字符串过滤并收集
 * 【练习目标】：
 *   熟练掌握真实业务中常见的“文本协议解析”、“脏数据清洗”以及“格式化重组与收集到 Map”等操作。
 *
 * 【需求清单】：
 *   数据源：["zhangsan, 23", "lisi, 24", "wangwu, 25", "zhaoliu, 26", "zhangsan, 23"]
 *   1. 练习一：保留年龄 >= 24 岁的人，并将结果收集到 Map 中，姓名为 Key，年龄为 Value。
 *   2. 练习二：脏数据清洗实战：去除首尾空格、跳过空白行、全转小写、去重并拼接。
 *   3. 练习三：按年龄奇偶性对数据源分类存入 Map<String, List<String>>。
 */
public class D07_PracticeStringFilterAndCollect {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块七】综合练习 2：字符串过滤、清洗与收集");
        System.out.println("==================================================");

        List<String> rawUsers = List.of(
                "zhangsan, 23",
                "lisi, 24",
                "wangwu, 25",
                "zhaoliu, 26",
                "zhangsan, 23"
        );
        System.out.println("原始用户数据: " + rawUsers);

        /* ---------------------------------------------------------
         * 练习 1：保留年龄 >= 24 岁的人并收集到 Map
         * --------------------------------------------------------- */
        System.out.println("\n--- 练习 1：保留年龄 >= 24 岁的人，收集到 Map (姓名 -> 年龄) ---");
        Map<String, Integer> adultMap = rawUsers.stream()
                .filter(item -> {
                    String[] parts = item.split(",");
                    int age = Integer.parseInt(parts[1].trim());
                    return age >= 24;
                })
                .collect(Collectors.toMap(
                        item -> item.split(",")[0].trim(),
                        item -> Integer.parseInt(item.split(",")[1].trim()),
                        (existing, replacement) -> existing // 出现同名时保留前者
                ));
        System.out.println("收集到的 Map 结果: " + adultMap);

        /* ---------------------------------------------------------
         * 练习 2：脏数据清洗流水线
         * --------------------------------------------------------- */
        System.out.println("\n--- 练习 2：生产级脏日志/数据清洗流水线 ---");
        List<String> dirtyLogs = List.of(
                "  INFO: User login SUCCESS  ",
                "",
                "   ",
                "ERROR: Network timeout",
                "INFO: User login SUCCESS",
                "warn: Low disk space  "
        );
        System.out.println("清洗前日志条数: " + dirtyLogs.size());

        String cleanedJoined = dirtyLogs.stream()
                .map(String::trim)                         // 去除两端空白字符
                .filter(line -> !line.isEmpty())           // 过滤空行
                .map(String::toUpperCase)                  // 统一转换为大写规范
                .distinct()                                // 去重
                .collect(Collectors.joining(" | "));        // 拼接为单行便于审计

        System.out.println("清洗并规范化后的日志流水线:");
        System.out.println(cleanedJoined);

        /* ---------------------------------------------------------
         * 练习 3：按年龄奇偶性分类
         * --------------------------------------------------------- */
        System.out.println("\n--- 练习 3：按年龄奇偶性分类归档 ---");
        Map<String, List<String>> parityMap = rawUsers.stream()
                .distinct()
                .collect(Collectors.groupingBy(item -> {
                    int age = Integer.parseInt(item.split(",")[1].trim());
                    return age % 2 == 0 ? "偶数年龄组" : "奇数年龄组";
                }));

        parityMap.forEach((group, members) ->
                System.out.println("  [" + group + "]: " + members));
        System.out.println();
    }
}
