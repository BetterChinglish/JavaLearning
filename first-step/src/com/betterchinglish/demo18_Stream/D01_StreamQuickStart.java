package com.betterchinglish.demo18_Stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 模块一：初爽 Stream 流（体验流式编程的魅力与传统写法的对比）
 * ----------------------------------------------------------------------
 * 【对应课程】：Stream流-01-初爽Stream流
 * 【核心诉求】：
 *   现有姓名列表：["张三丰", "张无忌", "张翠山", "王二麻子", "张良", "谢逊"]
 *   需求：
 *   1. 只要姓“张”的；
 *   2. 只要名字长度为 3 个字的；
 *   3. 遍历输出符合条件的名字。
 *
 * 【对比总结】：
 *   1. 传统写法：命令式编程（How to do）—— 关注过程细节，需要定义多个临时容器，频繁嵌套循环，代码繁琐冗长。
 *   2. Stream 写法：声明式编程（What to do）—— 关注计算目标，像流水线一样把数据通过传送带依次处理，优雅直观！
 */
public class D01_StreamQuickStart {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块一】初爽 Stream 流（传统循环 vs Stream 流）");
        System.out.println("==================================================");

        List<String> list = new ArrayList<>();
        Collections.addAll(list, "张三丰", "张无忌", "张翠山", "王二麻子", "张良", "谢逊");
        System.out.println("原始名单: " + list);

        /* ---------------------------------------------------------
         * 1. 传统做法：定义多个临时中间集合，多次遍历过滤
         * --------------------------------------------------------- */
        System.out.println("\n--- 1. 传统集合遍历过滤方式 ---");
        // 步骤一：筛选出姓“张”的人，存入新集合
        List<String> zhangList = new ArrayList<>();
        for (String name : list) {
            if (name.startsWith("张")) {
                zhangList.add(name);
            }
        }
        System.out.println("过滤姓张后: " + zhangList);

        // 步骤二：在姓“张”的集合中，继续筛选名字长度为 3 的人
        List<String> len3List = new ArrayList<>();
        for (String name : zhangList) {
            if (name.length() == 3) {
                len3List.add(name);
            }
        }
        System.out.println("过滤长度为3后: " + len3List);

        // 步骤三：遍历输出
        System.out.print("传统方式最终输出: ");
        for (String name : len3List) {
            System.out.print(name + " ");
        }
        System.out.println();

        /* ---------------------------------------------------------
         * 2. Stream 流式编程写法：链式调用，一气呵成
         * --------------------------------------------------------- */
        System.out.println("\n--- 2. Stream 流式编程极简写法 ---");
        System.out.print("Stream 链式调用最终输出: ");
        list.stream()
                .filter(name -> name.startsWith("张"))   // 中间操作：过滤姓张
                .filter(name -> name.length() == 3)       // 中间操作：过滤长度为3
                .forEach(name -> System.out.print(name + " ")); // 终结操作：遍历打印
        System.out.println();

        /* ---------------------------------------------------------
         * 3. 核心优势点透
         * --------------------------------------------------------- */
        System.out.println("\n--- 3. 为什么需要 Stream 流？ ---");
        System.out.println("1. 零临时中间集合开销：Stream 是对数据的操作视图，不产生像 zhangList/len3List 那样占用内存的中间容器；");
        System.out.println("2. 惰性求值与流水线合并：多个 filter 等中间操作在触发终结操作前不会执行，甚至能做指令级合并；");
        System.out.println("3. 声明式函数式代码：极大地提升了代码的可读性与维护性，让业务逻辑一目了然！\n");
    }
}
