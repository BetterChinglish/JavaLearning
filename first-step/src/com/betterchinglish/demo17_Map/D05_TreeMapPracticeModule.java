package com.betterchinglish.demo17_Map;

import com.betterchinglish.demo17_Map.model.Student;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 模块五：TreeMap 全套实战（自然排序、自定义比较器与字符统计）
 * ----------------------------------------------------------------------
 * 【对应课程】：集合进阶-10、11、12
 * 【核心特征】：
 * 1. 底层基于红黑树实现，Key 处于自动排好序的状态。
 * 2. Key 排序的两种方式：
 *    - 自然排序：Key 实现 Comparable<T> 接口
 *    - 比较器排序：构造 TreeMap 时传入外部 Comparator<T>（优先级更高）
 * 3. 经典练习：统计字符串中每个字符出现的频次，并按字典升序输出。
 */
public class D05_TreeMapPracticeModule {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块五】TreeMap 实战演练：自然排序与字符频次统计");
        System.out.println("==================================================");

        /* ---------------------------------------------------------
         * 1. 基础自然排序（以整数 ID 为 Key）
         * --------------------------------------------------------- */
        System.out.println("--- 1. 基础自然升序 (Integer Key) ---");
        Map<Integer, String> productMap = new TreeMap<>();
        productMap.put(1003, "手机");
        productMap.put(1001, "笔记本");
        productMap.put(1004, "平板");
        productMap.put(1002, "耳机");
        System.out.println("TreeMap 自动按 Key 升序排列结果: " + productMap);

        /* ---------------------------------------------------------
         * 2. 自定义对象作为 Key：自然排序 (Student compareTo: 年龄升序)
         * --------------------------------------------------------- */
        System.out.println("\n--- 2. 自定义对象 Student 作为 Key（自然排序：按年龄升序） ---");
        Map<Student, String> stuTreeMap = new TreeMap<>();
        stuTreeMap.put(new Student(101, "Alice", 20), "江苏");
        stuTreeMap.put(new Student(102, "Bob", 18), "浙江");
        stuTreeMap.put(new Student(103, "Charlie", 22), "上海");
        stuTreeMap.put(new Student(104, "David", 19), "北京");

        stuTreeMap.forEach((stu, address) ->
                System.out.println("  " + stu + " -> 所在地: " + address)
        );

        /* ---------------------------------------------------------
         * 3. 自定义比较器排序：外部 Comparator（覆盖自然排序）
         *    需求：按学生姓名长度降序排列；长度相同按字典序
         * --------------------------------------------------------- */
        System.out.println("\n--- 3. 外部比较器排序 Comparator（姓名长度降序） ---");
        Map<Student, String> customTreeMap = new TreeMap<>(
                Comparator.comparingInt((Student s) -> s.getName().length()).reversed()
                        .thenComparing(Student::getName)
        );
        customTreeMap.putAll(stuTreeMap);
        customTreeMap.forEach((stu, address) ->
                System.out.println("  " + stu + " -> 所在地: " + address)
        );

        /* ---------------------------------------------------------
         * 4. 经典统计练习：统计字符串字符频次并按字典序排列
         *    字符串："aababcabcdabcde" -> 输出 a(5)b(4)c(3)d(2)e(1)
         * --------------------------------------------------------- */
        System.out.println("\n--- 4. 经典练习：统计字符串中每个字符频次 (自动字典序) ---");
        String text = "aababcabcdabcde";
        Map<Character, Integer> charCountMap = new TreeMap<>();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            charCountMap.put(ch, charCountMap.getOrDefault(ch, 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        charCountMap.forEach((ch, count) -> sb.append(ch).append("(").append(count).append(")"));
        System.out.println("原始字符串: " + text);
        System.out.println("频次统计并升序排列结果: " + sb);
        System.out.println();
    }
}
