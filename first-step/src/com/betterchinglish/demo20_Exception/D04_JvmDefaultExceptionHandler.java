package com.betterchinglish.demo20_Exception;

/**
 * 模块四：JVM 虚拟机默认处理异常的方式
 * ----------------------------------------------------------------------
 * 【对应课程】：异常-04-JVM虚拟机默认处理异常的方式
 * 【核心流程】：
 *   当代码执行到某一处发生异常，且【没有任何 try...catch 进行捕获拦截】时，该异常最终会被抛给 JVM 虚拟机。
 *   JVM 的默认处理机制非常果断，包含固定两步：
 *   1. 【打印红色错误日志】：将异常的类型名称、详细原因以及发生异常的代码调用链路（包含类名、方法名、行号）
 *                         以标准错误流 (System.err) 打印到控制台；
 *   2. 【强行中断停止程序】：立即终止当前抛出异常的线程，后续的所有代码都将彻底失去执行机会！
 */
public class D04_JvmDefaultExceptionHandler {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块四】JVM 虚拟机默认处理异常的方式详解");
        System.out.println("==================================================");

        System.out.println("JVM 遇到未捕获异常时的两步默认行为：");
        System.out.println("  第一步：以红色字样 (System.err) 把异常名称、原因及调用位置信息打印在控制台；");
        System.out.println("  第二步：立即终止程序运行（停止 JVM/线程），后续代码全部不再执行！\n");

        /* ---------------------------------------------------------
         * 模拟演示 JVM 默认处理行为（在独立子线程中触发，避免中断主线程）
         * --------------------------------------------------------- */
        System.out.println("--- 模拟演示未捕获异常被 JVM 处理的过程 ---");
        Thread badWorker = new Thread(() -> {
            System.out.println("[子线程] 开始执行任务...");
            int[] arr = {10, 20, 30};
            // 访问越界下标 5，此子线程没有 try-catch
            System.out.println(arr[5]);
            System.out.println("[子线程] 任务成功完成！(如果触发异常，此句绝不会被打印)");
        }, "Exception-Demo-Thread");

        badWorker.start();

        try {
            // 等待子线程执行完毕，观察控制台由 JVM 打印的默认红字堆栈
            badWorker.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n【观察结论】：");
        System.out.println("子线程因为未捕获的 ArrayIndexOutOfBoundsException 被 JVM 强行杀死，");
        System.out.println("所以子线程后方的'[子线程] 任务成功完成'未打印；");
        System.out.println("正是因为 JVM 默认处理方式会直接'弄死'程序，我们才必须学习使用 try...catch 进行优雅捕获！\n");
    }
}
