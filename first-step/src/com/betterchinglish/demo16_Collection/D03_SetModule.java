package com.betterchinglish.demo16_Collection;

import com.betterchinglish.demo16_Collection.model.Student;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * 模块三：Set 体系深度精讲（HashSet, LinkedHashSet, TreeSet）
 * -------------------------------------------------------------
 * Set 特征：元素无序（不保证存取一致）、不可重复、没有索引。
 */
public class D03_SetModule {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块三】Set 体系：去重机制与排序");
        System.out.println("==================================================");

        /* =========================================================
         * 1. HashSet：基于哈希表去重
         * ========================================================= */
        System.out.println("--- 1. HashSet 基础与自定义对象去重 ---");
        Set<String> strSet = new HashSet<>();
        strSet.add("Java");
        strSet.add("Python");
        strSet.add("Go");
        strSet.add("Java"); // 重复添加，会被自动过滤
        System.out.println("字符串 HashSet (已自动去重): " + strSet);

        // 自定义对象去重：依赖 equals() 和 hashCode()
        Set<Student> stuSet = new HashSet<>();
        stuSet.add(new Student(101, "Alice", 95));
        stuSet.add(new Student(102, "Bob", 88));
        stuSet.add(new Student(101, "Alice", 95)); // id 和 name 完全相同

        System.out.println("Student HashSet 去重后大小: " + stuSet.size());
        for (Student s : stuSet) {
            System.out.println("   " + s);
        }
        System.out.println("【核心原理】HashSet 添加元素时：");
        System.out.println("  1. 计算对象的 hashCode() 确定在哈希表中的桶位置。");
        System.out.println("  2. 若位置为空直接放入；若冲突，进一步调用 equals()。");
        System.out.println("  3. 只有当 hashCode 相同 且 equals 返回 true 时，才判定为重复而不加入！");

        /* =========================================================
         * 2. LinkedHashSet：维护双向链表，保持插入顺序
         * ========================================================= */
        System.out.println("\n--- 2. LinkedHashSet 保持存取顺序一致 ---");
        Set<String> linkedSet = new LinkedHashSet<>();
        linkedSet.add("First");
        linkedSet.add("Second");
        linkedSet.add("Third");
        linkedSet.add("First"); // 同样去重
        System.out.println("LinkedHashSet 遍历顺序始终等于插入顺序: " + linkedSet);

        /* =========================================================
         * 3. TreeSet：基于红黑树的有序集合
         * ========================================================= */
        System.out.println("\n--- 3. TreeSet 自动排序 ---");

        // 3.1 自然排序（依托 Student 实现的 Comparable 接口：按分数降序）
        Set<Student> naturalTree = new TreeSet<>();
        naturalTree.add(new Student(101, "Jack", 70));
        naturalTree.add(new Student(102, "Tom", 99));
        naturalTree.add(new Student(103, "Lucy", 85));
        naturalTree.add(new Student(104, "Rose", 85)); // 分数相同，按 id 升序

        System.out.println("TreeSet 自然排序（分数降序，相同按学号）:");
        for (Student s : naturalTree) {
            System.out.println("   " + s);
        }

        // 3.2 比较器定制排序（传入 Comparator，按名字长度升序，长度相同按名字字典序）
        Set<String> customTree = new TreeSet<>(
                Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())
        );
        customTree.add("TypeScript");
        customTree.add("C");
        customTree.add("Java");
        customTree.add("Rust");
        customTree.add("Go");

        System.out.println("TreeSet 定制排序（先按字符串长度，再按字典序）: " + customTree);
        System.out.println();
    }
}
