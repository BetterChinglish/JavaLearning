package com.betterchinglish.demo16_Collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 模块二：List 体系深度精讲（ArrayList 与 LinkedList）
 * -------------------------------------------------------------
 * List 特征：有序（存取顺序一致）、可重复、有精确的整数索引（Index）。
 */
public class D02_ListModule {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块二】List 体系：ArrayList 与 LinkedList 深度对比");
        System.out.println("==================================================");

        /* =========================================================
         * 1. ArrayList：带索引的增删改查
         * ========================================================= */
        System.out.println("--- 1. ArrayList 索引操作 ---");
        List<String> list = new ArrayList<>();
        list.add("Apple");       // 尾部添加
        list.add("Banana");
        list.add("Orange");
        list.add(1, "Grape");    // 指定索引插入，后续元素后移

        System.out.println("插入后 List: " + list);
        System.out.println("索引 2 的元素 (get): " + list.get(2));

        String oldVal = list.set(2, "Mango"); // 修改指定位置，返回被替换的旧值
        System.out.println("修改索引 2 为 Mango (旧值 " + oldVal + "): " + list);

        list.remove(0); // 根据索引删除
        System.out.println("删除索引 0 后: " + list);

        /* =========================================================
         * 2. ArrayList 底层原理与扩容机制剖析
         * ========================================================= */
        System.out.println("\n--- 2. ArrayList 底层扩容原理解析 ---");
        System.out.println("【底层结构】底层是 Object[] elementData 动态数组。");
        System.out.println("【无参构造】new ArrayList<>() 时，初始化为空数组 {}，不分配内存。");
        System.out.println("【首次添加】第一次 add 时，初始化默认容量 DEFAULT_CAPACITY = 10。");
        System.out.println("【扩容算法】当容量不足时触发 grow()：");
        System.out.println("            新容量 = oldCapacity + (oldCapacity >> 1)  -> 即扩容为原先的 1.5 倍。");
        System.out.println("【内存迁移】底层使用 Arrays.copyOf()，本质调用 System.arraycopy 底层本地方法。");
        System.out.println("【优缺权衡】查找快 O(1)（连续内存寻址）；插入/删除非尾部元素慢 O(n)（需要批量平移）。");

        /* =========================================================
         * 3. LinkedList：双向链表与头尾特有操作
         * ========================================================= */
        System.out.println("\n--- 3. LinkedList 双向链表特性 ---");
        // 使用具体的 LinkedList 类型以访问其头尾专属 API
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("A");
        linkedList.add("B");
        linkedList.add("C");

        linkedList.addFirst("HEAD"); // 头部插入 O(1)
        linkedList.addLast("TAIL");  // 尾部插入 O(1)
        System.out.println("LinkedList 头尾添加后: " + linkedList);

        System.out.println("获取首元素 getFirst: " + linkedList.getFirst());
        System.out.println("获取尾元素 getLast: " + linkedList.getLast());

        linkedList.removeFirst(); // 移除首节点
        linkedList.removeLast();  // 移除尾节点
        System.out.println("移除头尾后: " + linkedList);

        /* =========================================================
         * 4. 易错点：Arrays.asList 与 List.of (Java 9+) 的不可变坑
         * ========================================================= */
        System.out.println("\n--- 4. 常见避坑：固定长度与只读列表 ---");

        // 坑点 A: Arrays.asList 返回的是 java.util.Arrays 的内部受限实现，长度固定！
        List<String> fixedList = Arrays.asList("one", "two", "three");
        try {
            fixedList.add("four"); // 会抛出 UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("坑点 A [Arrays.asList]: 不支持 add/remove 改变长度！已捕获: " + e.getClass().getSimpleName());
        }

        // 坑点 B: Java 9+ 的 List.of 返回的是完全不可变对象，不可改也不支持 null！
        List<String> immutableList = List.of("x", "y");
        try {
            immutableList.set(0, "newX"); // 抛出异常
        } catch (UnsupportedOperationException e) {
            System.out.println("坑点 B [List.of]: 元素完全只读，不支持 set 修改！已捕获: " + e.getClass().getSimpleName());
        }

        // 正确做法：若需要可修改的集合，用 new ArrayList<>(集合) 包装一份
        List<String> modifiableList = new ArrayList<>(Arrays.asList("one", "two"));
        modifiableList.add("three");
        System.out.println("安全做法: new ArrayList<>(Arrays.asList(...)) 即可自由增删: " + modifiableList);
        System.out.println();
    }
}
