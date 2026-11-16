package com.betterchinglish.demo16_Collection;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 模块四：Queue 与 Deque 体系（单向队列、双端队列、栈与优先堆）
 * -------------------------------------------------------------
 * 包含：普通队列 Queue、高性能双端队列 ArrayDeque、优先队列 PriorityQueue。
 */
public class D04_QueueDequeModule {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块四】Queue 与 Deque 体系：先进先出与堆栈操作");
        System.out.println("==================================================");

        /* =========================================================
         * 1. Queue 基本队列操作（FIFO：先进先出）
         *    推荐使用 offer / poll / peek（失败返回 false/null，不抛异常）
         * ========================================================= */
        System.out.println("--- 1. Queue 基础操作（以 LinkedList 承载） ---");
        Queue<String> queue = new LinkedList<>();

        // 入队
        queue.offer("Task-1");
        queue.offer("Task-2");
        queue.offer("Task-3");
        System.out.println("入队后队列: " + queue);

        // 查看队首元素（不出队）
        System.out.println("队首元素 (peek): " + queue.peek());

        // 出队
        while (!queue.isEmpty()) {
            System.out.println("   出队: " + queue.poll() + "，剩余: " + queue);
        }

        // 队列为空时：poll 返回 null，而 remove 会抛出 NoSuchElementException
        System.out.println("空队列 poll 结果: " + queue.poll());

        /* =========================================================
         * 2. ArrayDeque：循环数组双端队列（现代栈与双端队列首选）
         * ========================================================= */
        System.out.println("\n--- 2. ArrayDeque 作为“栈 (Stack)”的高性能首选 ---");
        System.out.println("【注意】Java 官方不推荐使用 java.util.Stack（它是同步 Vector 的子类，锁开销大）。");
        System.out.println("        替代方案：使用 Deque 接口与 ArrayDeque 实现类！");

        Deque<String> stack = new ArrayDeque<>();
        // 压栈（LIFO：后进先出）
        stack.push("Page A");
        stack.push("Page B");
        stack.push("Page C");
        System.out.println("压栈后状态: " + stack);

        // 查看栈顶
        System.out.println("栈顶元素 (peek): " + stack.peek());

        // 弹栈
        while (!stack.isEmpty()) {
            System.out.println("   弹栈 (pop): " + stack.pop() + "，当前栈: " + stack);
        }

        /* =========================================================
         * 3. PriorityQueue：优先级队列（底层二叉堆）
         * ========================================================= */
        System.out.println("\n--- 3. PriorityQueue 优先级堆 ---");
        // 默认是“小顶堆”（自然排序升序，最小元素最先出队）
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(50);
        minHeap.offer(10);
        minHeap.offer(30);
        minHeap.offer(5);

        System.out.print("小顶堆依次出队: ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " "); // 输出 5 10 30 50
        }
        System.out.println();

        // 构造“大顶堆”（通过 Comparator 倒序）
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        maxHeap.offer(50);
        maxHeap.offer(10);
        maxHeap.offer(30);
        maxHeap.offer(5);

        System.out.print("大顶堆依次出队: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " "); // 输出 50 30 10 5
        }
        System.out.println("\n");
    }
}
