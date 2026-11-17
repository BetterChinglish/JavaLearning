package com.betterchinglish.demo17_Map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 模块二：Map 集合的三种经典遍历方式
 * ----------------------------------------------------------------------
 * 【对应课程】：集合进阶-03、04、05
 * 1. 方式一：键找值（keySet -> get）
 * 2. 方式二：键值对对象（entrySet -> getKey / getValue）
 * 3. 方式三：Lambda 表达式（forEach -> (k, v) -> ...）
 */
public class D02_MapTraverseModule {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块二】Map 集合的三种标准遍历方式");
        System.out.println("==================================================");

        Map<String, Integer> scoreMap = new HashMap<>();
        scoreMap.put("Alice", 98);
        scoreMap.put("Bob", 85);
        scoreMap.put("Charlie", 92);
        scoreMap.put("David", 76);

        /* =========================================================
         * 方式一：键找值遍历（基于 keySet）
         * 流程：先通过 map.keySet() 拿到所有 Key 的 Set 集合，
         *       然后遍历 Key 集合，再调用 map.get(key) 获取对应的 Value。
         * 缺点：每次 get(key) 都需要重新对 key 做哈希计算并查表，效率相对偏低。
         * ========================================================= */
        System.out.println("--- 方式一：键找值遍历 (keySet) ---");
        Set<String> keys = scoreMap.keySet();
        for (String key : keys) {
            Integer value = scoreMap.get(key);
            System.out.println("  [keySet 遍历] 姓名: " + key + " -> 分数: " + value);
        }

        /* =========================================================
         * 方式二：键值对 Entry 遍历（基于 entrySet）——【最推荐的传统方式】
         * 流程：通过 map.entrySet() 拿到 Map.Entry<K, V> 的 Set 集合。
         * 优势：Entry 对象内部同时封装了 Key 和 Value，一次性全部拿到，
         *       不需要再次调用 map.get(key) 进行哈希寻址，性能比方式一更好！
         * ========================================================= */
        System.out.println("\n--- 方式二：键值对 Entry 遍历 (entrySet) ---");
        Set<Map.Entry<String, Integer>> entries = scoreMap.entrySet();

        // A. 增强 for 遍历 Entry
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println("  [entrySet 增强for] 姓名: " + entry.getKey() + " -> 分数: " + entry.getValue());
        }

        // B. 迭代器遍历 Entry（适合在遍历中安全调用 entryIterator.remove()）
        System.out.println("  --- 迭代器遍历 Entry ---");
        Iterator<Map.Entry<String, Integer>> entryIterator = entries.iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, Integer> entry = entryIterator.next();
            System.out.println("  [entrySet 迭代器] " + entry.getKey() + " = " + entry.getValue());
        }

        /* =========================================================
         * 方式三：Lambda 表达式与 forEach (Java 8+) ——【现代代码最优雅】
         * 流程：直接调用 map.forEach(BiConsumer<K, V>)
         * 底层原理：底层本质依然是循环遍历 entrySet()，然后将 key 和 value 传给 Lambda！
         * ========================================================= */
        System.out.println("\n--- 方式三：Lambda 表达式 forEach ---");
        scoreMap.forEach((key, value) ->
                System.out.println("  [Lambda forEach] 姓名: " + key + " -> 分数: " + value)
        );
        System.out.println();
    }
}
