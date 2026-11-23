package com.betterchinglish.demo20_Exception;

/**
 * Java 异常处理体系教学总入口
 * -------------------------------------------------------------
 * 【学习路径】
 * 1. D01_ExceptionHierarchy          : 异常体系介绍（Throwable 根类、Error 与 Exception 本质区别）
 * 2. D02_CompileVsRuntimeException   : 编译时异常 (Checked) 与运行时异常 (Unchecked) 深度辨析
 * 3. D03_ExceptionRolesInCode        : 异常在代码中的两大作用（问题排查定位线索 + 阻断非法业务的特殊返回通道）
 * 4. D04_JvmDefaultExceptionHandler  : JVM 虚拟机默认处理异常的方式（红字打印 System.err + 强行杀死线程中断程序）
 * 5. D05_TryCatchBasics              : try...catch 捕获异常基础语法与程序不崩溃特性
 * 6. D06_TryCatchSoulQuestionsPart1  : 捕获异常灵魂四问（第①问：无异常时执行流程；第②问：多异常捕获与父子顺序铁律）
 * 7. D07_TryCatchSoulQuestionsPart2  : 捕获异常灵魂四问（第③问：未捕获异常的下场；第④问：try 内部报错后后续代码生死 + finally）
 * 8. D08_ExceptionCommonMethods     : 异常常见方法（getMessage, toString, printStackTrace 底层细节剖析）
 * 9. D09_ThrowAndThrowsModule        : 抛出异常机制详解（throw 与 throws 区别矩阵、自定义业务异常实战）
 *
 * 可以直接运行 main 方法进行全流程学习，也可以单独针对某个模块进行断点调试与实验。
 */
public class Test {

    public static void main(String[] args) {

        System.out.println("############################################################");
        System.out.println("###          欢迎进入 Java 异常处理机制完整教学实验          ###");
        System.out.println("############################################################\n");

        // 模块一：异常体系介绍（Throwable, Error, Exception）
        D01_ExceptionHierarchy.learn();

        // 模块二：编译时受检异常 vs 运行时非受检异常
        D02_CompileVsRuntimeException.learn();

        // 模块三：异常在代码中的两大核心作用
        D03_ExceptionRolesInCode.learn();

        // 模块四：JVM 虚拟机默认处理异常的方式（打印红字日志并中断执行）
        D04_JvmDefaultExceptionHandler.learn();

        // 模块五：try...catch 捕获异常基础语法
        D05_TryCatchBasics.learn();

        // 模块六：捕获异常灵魂四问（第①问 & 第②问）
        D06_TryCatchSoulQuestionsPart1.learn();

        // 模块七：捕获异常灵魂四问（第③问 & 第④问 + finally）
        D07_TryCatchSoulQuestionsPart2.learn();

        // 模块八：异常核心方法与 printStackTrace 底层机制
        D08_ExceptionCommonMethods.learn();

        // 模块九：抛出异常机制（throw vs throws 对比与自定义异常）
        D09_ThrowAndThrowsModule.learn();

        System.out.println("############################################################");
        System.out.println("###        恭喜！Java 异常处理机制全部核心模块演练完成       ###");
        System.out.println("############################################################");
    }
}
