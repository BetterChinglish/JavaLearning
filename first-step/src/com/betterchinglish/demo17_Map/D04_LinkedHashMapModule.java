package com.betterchinglish.demo17_Map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 模块四：LinkedHashMap 特性与 LRU 缓存实战
 * ----------------------------------------------------------------------
 * 【对应课程】：集合进阶-09
 * 【核心特征】：
 * 1. 继承自 HashMap，在哈希表基础上额外维护一条【全局双向链表】。
 * 2. 默认保持【存取顺序一致（Insertion Order）】。
 * 3. 进阶模式：可配置为【访问顺序（Access Order）】，常用于构建 LRU（Least Recently Used 最近最少使用）缓存！
 */
public class D04_LinkedHashMapModule {

    /**
     * 极简 LRU 缓存实现：基于 LinkedHashMap 的 removeEldestEntry
     */
    static class SimpleLRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;

        // accessOrder 设为 true：按访问顺序排序；false 为按插入顺序
        public SimpleLRUCache(int capacity) {
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            // 当元素个数超过设定的容量上限时，自动淘汰最久未被访问的头部老节点！
            return size() > capacity;
        }
    }

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块四】LinkedHashMap：存取顺序保证与 LRU 缓存");
        System.out.println("==================================================");

        /* ---------------------------------------------------------
         * 1. HashMap vs LinkedHashMap 存取顺序对比
         * --------------------------------------------------------- */
        System.out.println("--- 1. HashMap 与 LinkedHashMap 存取顺序直观对比 ---");
        Map<String, Integer> hm = new HashMap<>();
        hm.put("c", 300);
        hm.put("a", 100);
        hm.put("d", 400);
        hm.put("b", 200);
        System.out.println("HashMap 遍历顺序 (由 Hash 桶决定，无序):       " + hm);

        Map<String, Integer> lhm = new LinkedHashMap<>();
        lhm.put("c", 300);
        lhm.put("a", 100);
        lhm.put("d", 400);
        lhm.put("b", 200);
        System.out.println("LinkedHashMap 遍历顺序 (严格等于存入顺序):    " + lhm);

        /* ---------------------------------------------------------
         * 2. LRU (最近最少使用) 缓存机制实战
         * --------------------------------------------------------- */
        System.out.println("\n--- 2. 实战应用：手写 LRU 缓存 (容量为 3) ---");
        SimpleLRUCache<String, Integer> lru = new SimpleLRUCache<>(3);
        lru.put("Item1", 1);
        lru.put("Item2", 2);
        lru.put("Item3", 3);
        System.out.println("初始满容量缓存 (容量 3): " + lru);

        // 访问 Item1，按访问顺序模式，Item1 会被自动提到链表末尾（最新被用）
        System.out.println("访问 Item1 (get): " + lru.get("Item1"));
        System.out.println("访问后的缓存状态 (Item1 移至最活跃末尾): " + lru);

        // 插入第 4 个元素，超出容量 3，最久未被访问的 Item2 将被自动淘汰！
        lru.put("Item4", 4);
        System.out.println("放入 Item4 触发淘汰后的缓存 (淘汰了 Item2): " + lru);
        System.out.println();
    }
}
