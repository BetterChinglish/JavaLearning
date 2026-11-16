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
 *
 * 【重难点突破】：TreeSet 到底调用 compare 还是 compareTo？
 * 结论：
 *   1. 没传 Comparator：强转为 Comparable，调用元素自身的 compareTo()。
 *      若元素未实现 Comparable，直接抛 ClassCastException！
 *   2. 传了 Comparator：优先调用 comparator.compare()，元素无需实现 Comparable。
 *   3. 两者并存时：外部传入的 Comparator 具有绝对优先级！
 *   4. 去重依据：仅看比较返回值是否为 0（cmp == 0 即判重），完全不看 equals 和 hashCode！
 */
public class D03_SetModule {

    /**
     * 辅助类：故意不实现 Comparable 接口，专门用于对比测试
     */
    static class Book {
        String title;
        double price;

        Book(String title, double price) {
            this.title = title;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Book{title='" + title + "', price=" + price + "}";
        }
    }

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块三】Set 体系：去重机制与排序（深度解密 TreeSet）");
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
         * 3. TreeSet 深度解密：到底用 compare 还是 compareTo？
         * ========================================================= */
        System.out.println("\n--- 3. TreeSet 排序与比较机制深度实战 ---");

        // ---------------------------------------------------------
        // 场景 A：无参构造 new TreeSet<>() -> 必须实现 Comparable，调用 compareTo()
        // ---------------------------------------------------------
        System.out.println("【场景 A】没传 Comparator 时：强制走 Comparable.compareTo()");
        Set<Student> naturalTree = new TreeSet<>();
        naturalTree.add(new Student(101, "Jack", 70));
        naturalTree.add(new Student(102, "Tom", 99));
        naturalTree.add(new Student(103, "Lucy", 85));
        naturalTree.add(new Student(104, "Rose", 85)); // 分数相同，按 id 升序
        System.out.println("  Student 自身实现了 Comparable，按成绩降序排序结果:");
        for (Student s : naturalTree) {
            System.out.println("     " + s);
        }

        // 坑点实战：未实现 Comparable 的类放入无参 TreeSet 会发生什么？
        Set<Book> trapTree = new TreeSet<>();
        try {
            trapTree.add(new Book("Java核心技术", 89.0));
            trapTree.add(new Book("Spring实战", 79.0)); // 抛出异常
        } catch (ClassCastException e) {
            System.out.println("  【踩坑验证】Book 未实现 Comparable，无参 TreeSet.add 直接抛异常: "
                    + e.getClass().getSimpleName() + " -> " + e.getMessage());
        }

        // ---------------------------------------------------------
        // 场景 B：带参构造 new TreeSet<>(Comparator) -> 调用 comparator.compare()
        // ---------------------------------------------------------
        System.out.println("\n【场景 B】传入 Comparator 时：走 comparator.compare()，元素无需实现 Comparable");
        // 传入 Comparator：按价格升序
        Set<Book> customBookTree = new TreeSet<>(Comparator.comparingDouble(b -> b.price));
        customBookTree.add(new Book("Java核心技术", 89.0));
        customBookTree.add(new Book("Spring实战", 79.0));
        customBookTree.add(new Book("Redis设计与实现", 59.0));
        System.out.println("  Book 未实现 Comparable，但传入 Comparator 成功排序:");
        for (Book b : customBookTree) {
            System.out.println("     " + b);
        }

        // ---------------------------------------------------------
        // 场景 C：两者并存时，谁的优先级更高？
        // ---------------------------------------------------------
        System.out.println("\n【场景 C】两者并存时的优先级测试：");
        System.out.println("  Student 自身内置的 compareTo() 是按【成绩降序】；");
        System.out.println("  现在外部构造 TreeSet 时传入 Comparator 按【姓名首字母升序】：");
        Set<Student> priorityTree = new TreeSet<>(Comparator.comparing(Student::getName));
        priorityTree.addAll(naturalTree);
        for (Student s : priorityTree) {
            System.out.println("     " + s); // 输出结果按姓名升序排列，验证了外部 Comparator 胜出！
        }
        System.out.println("  结论：传入的 Comparator 具有【绝对优先覆盖权】！");

        // ---------------------------------------------------------
        // 场景 D：TreeSet 的去重唯一标准是 cmp == 0，与 equals/hashCode 无关！
        // ---------------------------------------------------------
        System.out.println("\n【场景 D】TreeSet 的去重陷阱：");
        // 构造一个只比较价格的 TreeSet
        Set<Book> priceOnlyTree = new TreeSet<>(Comparator.comparingDouble(b -> b.price));
        priceOnlyTree.add(new Book("书本A", 50.0));
        priceOnlyTree.add(new Book("书本B", 50.0)); // 价格相同，书名不同
        System.out.println("  存入两本价格相同但书名不同的书，结果集合大小: " + priceOnlyTree.size());
        System.out.println("  集合内容: " + priceOnlyTree);
        System.out.println("  【警示】：只要 compare 返回 0，TreeSet 就直接判定为重复而拒绝加入！");
        System.out.println();
    }
}
