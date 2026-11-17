package com.betterchinglish.demo17_Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 模块七：TreeMap 核心源码剖析、可变参数与 Collections 工具类
 * ----------------------------------------------------------------------
 * 【对应课程】：集合进阶-17、18、19、20
 * 1. TreeMap 源码篇：Entry 结构与 put 核心红黑树修复链（fixAfterInsertion）
 * 2. 可变参数（Varargs）：语法、本质数组、注意事项
 * 3. 集合工具类 Collections：批量添加 addAll、不可变集合包装、单列与双列集合增强
 */
public class D07_TreeMapSourceAndCollectionsModule {

    /* =========================================================
     * 可变参数演示方法
     * 规则：
     * 1. 语法：类型... 参数名
     * 2. 底层本质：编译器自动将其包装为一个【数组】！
     * 3. 约束：形参列表中只能有一个可变参数，且必须放在【最后一个位置】！
     * ========================================================= */
    public static int sum(int initialBonus, int... numbers) {
        int total = initialBonus;
        // numbers 在方法内部完全当作数组使用
        for (int n : numbers) {
            total += n;
        }
        return total;
    }

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块七】TreeMap 源码精讲、可变参数与 Collections 工具类");
        System.out.println("==================================================");

        /* =========================================================
         * 1. TreeMap 源码篇（一 & 二）：put 与 fixAfterInsertion
         * ========================================================= */
        System.out.println("--- 1. TreeMap 源码设计精要 ---");
        System.out.println("【核心节点结构】static final class Entry<K,V> implements Map.Entry<K,V> {");
        System.out.println("                 K key; V value; Entry<K,V> left; Entry<K,V> right; Entry<K,V> parent; boolean color;");
        System.out.println("               } -> 典型的自平衡二叉查找树红黑树节点。");
        System.out.println("【put 流程】：");
        System.out.println("  1. 若 root 为空，直接 new Entry 作为根节点，强制染为 BLACK 黑色。");
        System.out.println("  2. 若 root 非空，优先依据 comparator（若无则强转 Comparable）沿着树向下比较查找插入点。");
        System.out.println("  3. 若找到 cmp == 0 的 key，直接用新 value 替换旧 value 并返回旧值。");
        System.out.println("  4. 若未找到，创建红色新节点，挂载到对应父节点的 left 或 right 上。");
        System.out.println("  5. 调用核心平衡修复方法：fixAfterInsertion(Entry<K,V> x) 执行【变色与左/右旋转】！");

        /* =========================================================
         * 2. 可变参数（Varargs）实战与陷阱
         * ========================================================= */
        System.out.println("\n--- 2. 可变参数 (Varargs) 实战 ---");
        int res1 = sum(100);                    // 传 0 个可变参数
        int res2 = sum(100, 10, 20);            // 传 2 个可变参数
        int res3 = sum(100, 1, 2, 3, 4, 5);     // 传多个可变参数
        int res4 = sum(100, new int[]{5, 15});  // 直接传入一个数组
        System.out.println("可变参数计算结果: " + res1 + ", " + res2 + ", " + res3 + ", " + res4);

        /* =========================================================
         * 3. 集合工具类 Collections 核心功能
         * ========================================================= */
        System.out.println("\n--- 3. Collections 工具类高级操作 ---");

        // 3.1 批量添加 Collections.addAll（基于可变参数）
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "Java", "Python", "Golang", "Rust", "C++");
        System.out.println("Collections.addAll 批量添加: " + list);

        // 3.2 排序与反转
        Collections.sort(list);
        System.out.println("升序排序 (sort): " + list);
        Collections.reverse(list);
        System.out.println("反转列表 (reverse): " + list);

        // 3.3 随机打乱
        Collections.shuffle(list);
        System.out.println("随机洗牌 (shuffle): " + list);

        // 3.4 二分查找（先排序）
        Collections.sort(list);
        int idx = Collections.binarySearch(list, "Golang");
        System.out.println("二分查找 'Golang' 下标: " + idx);

        // 3.5 只读不可变视图（防御性编程）
        Map<String, String> originalMap = Map.of("K1", "V1", "K2", "V2"); // Java 9+ 不可变 Map
        Map<String, String> unmodifiable = Collections.unmodifiableMap(originalMap);
        System.out.println("不可变 Map: " + unmodifiable);
        System.out.println();
    }
}
