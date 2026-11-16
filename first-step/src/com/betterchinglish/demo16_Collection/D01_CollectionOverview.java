package com.betterchinglish.demo16_Collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 模块一：Collection 顶层接口与公共 API 教学
 * -------------------------------------------------------------
 * Collection 是所有单列集合（List, Set, Queue）的祖宗接口。
 * 这里定义的方法，在所有子实现类中都通用！
 */
public class D01_CollectionOverview {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块一】Collection 顶层接口与核心公共方法演示");
        System.out.println("==================================================");

        // 多态：父接口引用指向具体实现类（这里以 ArrayList 为例承载）
        Collection<String> coll = new ArrayList<>();

        /* ---------------------------------------------------------
         * 1. add(E e) - 添加单个元素
         *    List 允许重复，恒返回 true；Set 若元素已存在则返回 false
         * --------------------------------------------------------- */
        coll.add("Java");
        coll.add("Spring");
        coll.add("Redis");
        coll.add("Java"); // List 支持重复元素
        System.out.println("1. 添加元素后集合: " + coll);

        /* ---------------------------------------------------------
         * 2. size() 与 isEmpty() - 容量与判空
         * --------------------------------------------------------- */
        System.out.println("2. 集合元素个数: " + coll.size());
        System.out.println("   集合是否为空: " + coll.isEmpty());

        /* ---------------------------------------------------------
         * 3. contains(Object o) - 是否包含某元素
         *    底层依赖元素自身的 equals 方法进行比对
         * --------------------------------------------------------- */
        System.out.println("3. 是否包含 \"Spring\": " + coll.contains("Spring"));
        System.out.println("   是否包含 \"Python\": " + coll.contains("Python"));

        /* ---------------------------------------------------------
         * 4. remove(Object o) - 删除元素
         *    若有多个相同元素，只会删除满足 equals 的第一个
         * --------------------------------------------------------- */
        boolean removed = coll.remove("Java");
        System.out.println("4. 删除 \"Java\" 结果: " + removed + "，当前集合: " + coll);

        /* ---------------------------------------------------------
         * 5. addAll(Collection<? extends E> c) - 批量添加
         * --------------------------------------------------------- */
        Collection<String> other = Arrays.asList("MySQL", "Kafka");
        coll.addAll(other);
        System.out.println("5. 批量添加后集合: " + coll);

        /* ---------------------------------------------------------
         * 6. toArray() - 集合转数组
         *    推荐使用带泛型类型的 toArray(T[] a)，避免类型强转风险
         * --------------------------------------------------------- */
        String[] arr = coll.toArray(new String[0]);
        System.out.println("6. 集合转数组: " + Arrays.toString(arr));

        /* ---------------------------------------------------------
         * 7. clear() - 清空集合所有元素
         * --------------------------------------------------------- */
        coll.clear();
        System.out.println("7. clear 清空后元素个数: " + coll.size() + "，是否为空: " + coll.isEmpty());
        System.out.println();
    }
}
