package com.betterchinglish.demo16_Collection;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/**
 * 模块七：Collection 体系“顺序特性（Order）”横向深度对比
 * ----------------------------------------------------------------------
 * 【核心问题】：Collection 体系添加元素是有顺序的吗？
 * 【权威答案】：不能一概而论，必须看具体的实现类！
 *
 * 【概念拆解——千万别把“两种顺序”混为一谈】：
 * 1. 存取顺序（Insertion Order）：
 *    - 含义：你存入的先后顺序（First-In），是否等于遍历遍历出来的先后顺序。
 * 2. 大小顺序（Sorted Order）：
 *    - 含义：元素不管按什么先后顺序添加，底层是否自动按照元素本身的大小（Comparable/Comparator）排好序。
 * 3. 无序（Unordered）：
 *    - 含义：既不保证存取顺序一致，也不按元素大小排序。
 *
 * 【重大误区澄清】：
 * 误区：“HashSet 是无序的，所以每次循环打印出来的顺序都是随机乱变的？”
 * 正解：错！在未触发扩容且数据不变的情况下，同一个 JVM 运行期间 HashSet 的遍历顺序通常是固定不变的。
 *      所谓的“无序”是指：它的顺序完全由对象的 hashCode 映射出的数组桶位置决定，
 *      【不保证等于你的存入顺序】，且一旦发生扩容（rehash），顺序就可能彻底重排。
 */
public class D07_CollectionOrderModule {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块七】Collection 体系“顺序特性”横向大比拼");
        System.out.println("==================================================");

        // 准备相同的输入数据序列（故意打乱添加顺序）
        String[] seasons = {"Spring", "Summer", "Autumn", "Winter"};
        System.out.println("【基准输入】添加顺序依次为: [Spring, Summer, Autumn, Winter]\n");

        /* =========================================================
         * 对比 1：List 体系 —— 强保证存取顺序（Insertion Order）
         * ========================================================= */
        List<String> arrayList = new ArrayList<>();
        for (String s : seasons) {
            arrayList.add(s);
        }
        System.out.println("1. ArrayList (List 体系):");
        System.out.println("   输出结果: " + arrayList);
        System.out.println("   顺序结论: 【严格保证存取顺序】。底层是连续数组，通过递增索引记录位置。");
        System.out.println();

        /* =========================================================
         * 对比 2：HashSet —— 无序（Unordered）
         * ========================================================= */
        Set<String> hashSet = new HashSet<>();
        for (String s : seasons) {
            hashSet.add(s);
        }
        System.out.println("2. HashSet (Set 体系):");
        System.out.println("   输出结果: " + hashSet);
        System.out.println("   顺序结论: 【无序】。顺序由字符串对象的 hashCode() 与数组长度决定的桶下标决定，");
        System.out.println("             既与添加先后无关，也不是按字母 a-z 排序。");
        System.out.println();

        /* =========================================================
         * 对比 3：LinkedHashSet —— 兼具去重与严格存取顺序（Insertion Order）
         * ========================================================= */
        Set<String> linkedHashSet = new LinkedHashSet<>();
        for (String s : seasons) {
            linkedHashSet.add(s);
        }
        System.out.println("3. LinkedHashSet (Set 体系):");
        System.out.println("   输出结果: " + linkedHashSet);
        System.out.println("   顺序结论: 【严格保证存取顺序】。底层是哈希表 + 额外维护的一条双向链表，");
        System.out.println("             既享有哈希去重的高性能，又保留了存入时的先后次序。");
        System.out.println();

        /* =========================================================
         * 对比 4：TreeSet —— 大小排序（Sorted Order，非存取顺序）
         * ========================================================= */
        Set<String> treeSet = new TreeSet<>();
        for (String s : seasons) {
            treeSet.add(s);
        }
        System.out.println("4. TreeSet (Set 体系):");
        System.out.println("   输出结果: " + treeSet);
        System.out.println("   顺序结论: 【保证大小排序，不保证存取顺序】。底层基于红黑树，");
        System.out.println("             自动按自然排序规则（字符串首字母 A-Z）升序重排。");
        System.out.println();

        /* =========================================================
         * 对比 5：Queue / Deque / PriorityQueue —— 队列出队顺序
         * ========================================================= */
        System.out.println("5. Queue / Deque 体系的出队顺序:");

        // A. ArrayDeque 作为 FIFO 队列
        Queue<String> fifoQueue = new ArrayDeque<>();
        for (String s : seasons) {
            fifoQueue.offer(s);
        }
        System.out.print("   ArrayDeque (FIFO 队列出队): ");
        while (!fifoQueue.isEmpty()) {
            System.out.print(fifoQueue.poll() + " -> ");
        }
        System.out.println("null (严格先进先出)");

        // B. ArrayDeque 作为 LIFO 栈
        Deque<String> lifoStack = new ArrayDeque<>();
        for (String s : seasons) {
            lifoStack.push(s);
        }
        System.out.print("   ArrayDeque (LIFO 栈出栈):   ");
        while (!lifoStack.isEmpty()) {
            System.out.print(lifoStack.pop() + " -> ");
        }
        System.out.println("null (严格后进先出)");

        // C. PriorityQueue 优先级堆出队
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        int[] nums = {50, 10, 40, 20, 30};
        for (int n : nums) {
            priorityQueue.offer(n);
        }
        System.out.print("   PriorityQueue (入堆 50,10,40,20,30 出队): ");
        while (!priorityQueue.isEmpty()) {
            System.out.print(priorityQueue.poll() + " ");
        }
        System.out.println("(按二叉小顶堆优先级出队，不是存入顺序)");

        /* =========================================================
         * 总结速查表（方便学习复习与面试回答）
         * ========================================================= */
        printOrderSummaryTable();
    }

    /**
     * 顺序特性总结速查与面试回答口诀
     */
    private static void printOrderSummaryTable() {
        System.out.println("\n----------------------------------------------------------------------");
        System.out.println("【Collection 体系“顺序特性”终极口诀与选型表】");
        System.out.println("----------------------------------------------------------------------");
        System.out.println("| 实现类           | 存取顺序 (Insertion) | 大小排序 (Sorted) | 底层结构决定原理                  |");
        System.out.println("| ArrayList        |        有           |        无         | 动态数组，按连续索引存储          |");
        System.out.println("| LinkedList       |        有           |        无         | 双向链表，按指针首尾相连          |");
        System.out.println("| HashSet          |        无           |        无         | 哈希表，由 hashCode 算桶下标决定  |");
        System.out.println("| LinkedHashSet    |        有           |        无         | 哈希表 + 维护双向链表记录插入先后 |");
        System.out.println("| TreeSet          |        无           |        有         | 红黑树，按 Comparable 比较大小    |");
        System.out.println("| ArrayDeque       |        有           |        无         | 循环数组，严格遵循 FIFO 或 LIFO   |");
        System.out.println("| PriorityQueue    |        无           |     按优先级      | 二叉堆，始终优先弹出极值          |");
        System.out.println("----------------------------------------------------------------------\n");
    }
}
