package com.betterchinglish.demo20_Exception;

/**
 * 模块五：try...catch 捕获异常基础语法与执行流程
 * ----------------------------------------------------------------------
 * 【对应课程】：异常-05-try...catch捕获异常
 * 【基本格式】：
 *      try {
 *          // 可能出现异常的代码;
 *      } catch (异常类名 变量名) {
 *          // 异常发生时的处理代码 (如记录日志、提示用户、恢复默认值等);
 *      }
 *
 * 【核心价值】：
 *   自己编写 catch 块捕获异常，彻底剥夺 JVM 默认杀死程序的权利！
 *   即使发生了异常，程序被 catch 拦截处理之后，后续代码依然可以平稳、正常地继续执行下去！
 */
public class D05_TryCatchBasics {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块五】try...catch 捕获异常基础语法与不中断特性");
        System.out.println("==================================================");

        int[] arr = {1, 23, 4, 5, 6, 67};

        System.out.println("--- 1. 触发异常并由 catch 拦截 ---");
        try {
            System.out.println("正在尝试访问 arr[10]...");
            System.out.println(arr[10]); // 此处发生 ArrayIndexOutOfBoundsException
            System.out.println("try 中这行代码不会被执行！");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("--> catch 捕获成功！原因: 下标越界 (访问索引 10，数组长度仅为 " + arr.length + ")");
            System.out.println("--> 兜底处理完毕，业务可以继续推进！");
        }

        /* ---------------------------------------------------------
         * 2. 验证：try-catch 后面的后续代码依然能够平稳执行
         * --------------------------------------------------------- */
        System.out.println("\n--- 2. 验证后续代码执行状态 ---");
        System.out.println("程序顺利运行到 try-catch 下方，并没有被 JVM 杀死！");
        System.out.println("当前系统依然健康，正常处理下一笔订单业务...\n");
    }
}
