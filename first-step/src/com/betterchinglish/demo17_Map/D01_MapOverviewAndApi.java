package com.betterchinglish.demo17_Map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 模块一：双列集合特点与 Map 顶层常用 API
 * ----------------------------------------------------------------------
 * 【对应课程】：集合进阶-01、02
 * 【核心特征】：
 * 1. 双列集合一次存储一对数据：Key (键) 与 Value (值)，合称键值对（Entry）。
 * 2. 键（Key）：唯一、不可重复；值（Value）：允许重复。
 * 3. 每个 Key 只能映射到一个 Value 上。
 */
public class D01_MapOverviewAndApi {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块一】双列集合特点与 Map 顶层核心常用 API");
        System.out.println("==================================================");

        Map<String, String> map = new HashMap<>();

        /* ---------------------------------------------------------
         * 1. put(K key, V value) - 添加或覆盖
         *    注意返回值：如果 key 不存在，返回 null；
         *               如果 key 已存在，会用新 value 覆盖旧 value，并返回【被覆盖的旧值】！
         * --------------------------------------------------------- */
        System.out.println("--- 1. put 插入与覆盖演示 ---");
        String v1 = map.put("CN", "China");
        String v2 = map.put("US", "United States");
        String v3 = map.put("JP", "Japan");
        System.out.println("首次插入返回旧值: " + v1 + ", " + v2 + ", " + v3);
        System.out.println("当前 map: " + map);

        String oldVal = map.put("CN", "People's Republic of China"); // 重复 Key
        System.out.println("再次插入相同 Key 'CN'，返回被覆盖的旧值: " + oldVal);
        System.out.println("覆盖后 map: " + map);

        /* ---------------------------------------------------------
         * 2. get(Object key) 与 getOrDefault(key, defaultValue)
         * --------------------------------------------------------- */
        System.out.println("\n--- 2. get 与 getOrDefault ---");
        System.out.println("get('US'): " + map.get("US"));
        System.out.println("get('FR') [不存在时返回 null]: " + map.get("FR"));
        System.out.println("getOrDefault('FR', 'France') [设置保底默认值]: " + map.getOrDefault("FR", "France"));

        /* ---------------------------------------------------------
         * 3. containsKey 与 containsValue
         * --------------------------------------------------------- */
        System.out.println("\n--- 3. 判存方法 ---");
        System.out.println("是否包含 Key 'US': " + map.containsKey("US"));
        System.out.println("是否包含 Value 'Japan': " + map.containsValue("Japan"));

        /* ---------------------------------------------------------
         * 4. remove(Object key) 与 remove(key, value)
         * --------------------------------------------------------- */
        System.out.println("\n--- 4. remove 删除方法 ---");
        String removedVal = map.remove("JP");
        System.out.println("根据 Key 删除 'JP'，返回被删除的值: " + removedVal + "，删除后 map: " + map);

        // 重载版本：仅当 key 和 value 同时匹配时才执行删除
        boolean removeMatched = map.remove("US", "WrongValue");
        System.out.println("值不匹配时 remove('US', 'WrongValue'): " + removeMatched);

        /* ---------------------------------------------------------
         * 5. keySet(), values(), entrySet() - 视图获取
         * --------------------------------------------------------- */
        System.out.println("\n--- 5. 三种视图 ---");
        Set<String> keys = map.keySet();
        Collection<String> values = map.values();
        System.out.println("所有 Key 集合 (keySet): " + keys);
        System.out.println("所有 Value 集合 (values): " + values);

        /* ---------------------------------------------------------
         * 6. size, isEmpty 与 clear
         * --------------------------------------------------------- */
        System.out.println("\n--- 6. 状态与清空 ---");
        System.out.println("键值对总数 (size): " + map.size());
        System.out.println("是否为空 (isEmpty): " + map.isEmpty());
        map.clear();
        System.out.println("clear 清空后: " + map + "，大小: " + map.size());
        System.out.println();
    }
}
