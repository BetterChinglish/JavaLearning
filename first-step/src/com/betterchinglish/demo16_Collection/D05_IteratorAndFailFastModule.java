package com.betterchinglish.demo16_Collection;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/**
 * 模块五：遍历方式与快速失败机制（fail-fast）深度避坑
 * -------------------------------------------------------------
 * 重点攻克：
 * 1. 四种常见遍历方式
 * 2. 为什么在增强 for 中删除元素会抛 ConcurrentModificationException
 * 3. 快速失败背后的 modCount 原理与正确删除方案
 */
public class D05_IteratorAndFailFastModule {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块五】遍历方式与 fail-fast 快速失败深度避坑");
        System.out.println("==================================================");

        List<String> list = new ArrayList<>(List.of("Java", "Kotlin", "Go", "Rust"));

        /* =========================================================
         * 1. 常见 4 种遍历方式
         * ========================================================= */
        System.out.println("--- 1. 四种遍历方式演示 ---");

        // 方式 A: 普通 for 循环（仅限于有索引的 List）
        System.out.print("方式 A (普通 for): ");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();

        // 方式 B: 增强 for 循环（底层其实编译为 Iterator）
        System.out.print("方式 B (增强 for): ");
        for (String item : list) {
            System.out.print(item + " ");
        }
        System.out.println();

        // 方式 C: Iterator 迭代器（标准通用方式）
        System.out.print("方式 C (Iterator): ");
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();

        // 方式 D: Lambda 表达式与 forEach (Java 8+)
        System.out.print("方式 D (forEach): ");
        list.forEach(item -> System.out.print(item + " "));
        System.out.println();

        /* =========================================================
         * 2. 经典死坑：循环遍历时直接删除元素抛出 ConcurrentModificationException
         * ========================================================= */
        System.out.println("\n--- 2. 经典死坑：增强 for 中直接 remove ---");
        List<String> trapList = new ArrayList<>(List.of("A", "B", "C", "D"));

        try {
            for (String s : trapList) {
                if ("B".equals(s)) {
                    trapList.remove(s); // 致命错误：直接调用了 List 本身的 remove！
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("【成功捕获异常】已抛出: " + e.getClass().getSimpleName());
            System.out.println("【报错根源剖析】");
            System.out.println("  1. 增强 for 语法糖底层调用了迭代器的 next()。");
            System.out.println("  2. ArrayList 内部有一个 modCount（记录结构修改次数）。");
            System.out.println("  3. 迭代器创建时保存了 expectedModCount = modCount。");
            System.out.println("  4. 当直接调用 list.remove() 时，modCount 自增，但迭代器的 expectedModCount 没有同步！");
            System.out.println("  5. 下一次调用 it.next() 时触发 checkForComodification()，检测到不一致，立刻抛出异常！");
        }

        /* =========================================================
         * 3. 正确的删除方式（两种黄金方案）
         * ========================================================= */
        System.out.println("\n--- 3. 遍历删除的正确方案 ---");

        // 方案一：使用 Iterator 自身的 remove() 方法
        List<String> solution1 = new ArrayList<>(List.of("A", "B", "C", "D"));
        Iterator<String> iterator = solution1.iterator();
        while (iterator.hasNext()) {
            String val = iterator.next();
            if ("B".equals(val)) {
                iterator.remove(); // 关键：iterator.remove() 会在删除后同步 expectedModCount = modCount！
            }
        }
        System.out.println("方案一 [Iterator.remove()] 成功删除: " + solution1);

        // 方案二：使用 Java 8+ 的 removeIf(Predicate)（代码最优雅，首选）
        List<String> solution2 = new ArrayList<>(List.of("A", "B", "C", "D"));
        solution2.removeIf(val -> "B".equals(val) || "C".equals(val));
        System.out.println("方案二 [removeIf] 成功删除: " + solution2);
        System.out.println();
    }
}
