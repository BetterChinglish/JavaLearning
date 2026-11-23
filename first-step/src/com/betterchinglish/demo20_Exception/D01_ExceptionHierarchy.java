package com.betterchinglish.demo20_Exception;

/**
 * 模块一：异常体系介绍（Exception Hierarchy）
 * ----------------------------------------------------------------------
 * 【对应课程】：异常-01-异常体系介绍
 * 【核心认知】：
 *   异常是什么？
 *   —— 程序在运行或者编译过程中出现的非正常情况、错误与意外中断。
 *   在 Java 中，“一切皆对象”，发生异常时也是由 Java 将错误信息封装成一个“异常对象”抛出。
 *
 * 【继承体系全貌】：
 *                  java.lang.Throwable (顶层根类)
 *                          /          \
 *                         /            \
 *             java.lang.Error      java.lang.Exception
 *             (系统级/严重错误)       (程序级/可处理异常)
 *                                      /          \
 *                                     /            \
 *                      RuntimeException      编译时受检异常 (Checked)
 *                      (运行时非受检异常)       (如 IOException, ParseException)
 *
 * 【Error 与 Exception 的本质区别】：
 *   1. Error（严重错误）：
 *      - 通常是系统底层、硬件环境或 JVM 虚拟机严重故障（例如 StackOverflowError 栈溢出、OutOfMemoryError 堆内存溢出）。
 *      - 严重且无法自愈，不是程序员通过写代码就能修复的问题，不需要也不能用 try-catch 捕获，只能优化配置或重构算法。
 *   2. Exception（异常）：
 *      - 程序运行过程中出现的意外情况（例如除数为 0、数组越界、空指针、文件不存在）。
 *      - 这是程序员可以通过 Java 代码进行预测、捕获并处理的，处理得当程序可以恢复正常运行。
 */
public class D01_ExceptionHierarchy {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块一】Java 异常体系介绍与核心类关系");
        System.out.println("==================================================");

        /* ---------------------------------------------------------
         * 1. 顶层根类 Throwable
         * --------------------------------------------------------- */
        System.out.println("--- 1. 顶层根类：java.lang.Throwable ---");
        System.out.println("Throwable 是 Java 语言中所有错误 (Error) 和异常 (Exception) 的超类。");
        System.out.println("只有 Throwable 及其子类的实例，才能被 throw 语句抛出，或者作为 catch 子句的参数类型。\n");

        /* ---------------------------------------------------------
         * 2. Error 严重错误辨析
         * --------------------------------------------------------- */
        System.out.println("--- 2. Error 严重错误特点 ---");
        System.out.println("代表不可恢复的灾难性故障，例如：");
        System.out.println("  - StackOverflowError: 递归过深导致栈空间耗尽；");
        System.out.println("  - OutOfMemoryError (OOM): 堆内存耗尽，垃圾回收器也无法释放足够空间。");
        System.out.println("【处理原则】：不捕获、不处理，由 JVM 强行终止，开发者需排查代码死循环或调整 JVM 内存参数 (-Xmx)。\n");

        /* ---------------------------------------------------------
         * 3. Exception 异常特点
         * --------------------------------------------------------- */
        System.out.println("--- 3. Exception 异常分类 ---");
        System.out.println("Exception 是程序自身的容错保护机制，主要分为两大分支：");
        System.out.println("  - 运行时异常 (RuntimeException 及子类)：运行阶段暴露，通常由逻辑缺陷引起；");
        System.out.println("  - 编译时异常 (除 RuntimeException 以外的子类)：编译阶段由编译器强制检查！\n");
    }
}
