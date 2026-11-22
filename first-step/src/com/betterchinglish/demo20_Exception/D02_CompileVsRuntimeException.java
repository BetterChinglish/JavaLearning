package com.betterchinglish.demo20_Exception;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 模块二：编译时异常和运行时异常（Checked vs Unchecked Exception）
 * ----------------------------------------------------------------------
 * 【对应课程】：异常-02-编译时异常和运行时异常
 * 【二者本质差异】：
 *   1. 编译时异常（Checked Exception 受检异常）：
 *      - 范围：直接继承自 Exception，但不继承自 RuntimeException 的所有子类。
 *      - 特征：在编译阶段，Java 编译器就会强制检查！如果不显式进行处理（try-catch 捕获或 throws 抛出），代码直接标红编译不通过。
 *      - 目的：提醒程序员这里极容易出现外界环境引发的意外（如日期格式写错、文件不存在、网络中断），必须提前想好应对方案。
 *      - 常见代表：ParseException, IOException, FileNotFoundException, ClassNotFoundException, SQLException。
 *
 *   2. 运行时异常（Unchecked Exception 非受检异常）：
 *      - 范围：继承自 RuntimeException 或其子类。
 *      - 特征：编译阶段不会报错，编译器完全不强求处理；只有在代码实际运行且触发特定条件时才会爆出异常。
 *      - 目的：通常是程序员自身逻辑不严谨造成的 Bug（如下标越界、对象为 null、除数为 0）。
 *      - 常见代表：NullPointerException, ArrayIndexOutOfBoundsException, ArithmeticException, ClassCastException。
 */
public class D02_CompileVsRuntimeException {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块二】编译时异常 (Checked) 与运行时异常 (Unchecked)");
        System.out.println("==================================================");

        /* ---------------------------------------------------------
         * 1. 运行时异常演练 (编译完全通过，运行才报错)
         * --------------------------------------------------------- */
        System.out.println("--- 1. 运行时异常 (RuntimeException) 演示 ---");
        try {
            int a = 10;
            int b = 0;
            System.out.println("尝试进行除法运算: " + (a / b)); // 触发 ArithmeticException: / by zero
        } catch (ArithmeticException e) {
            System.out.println("成功捕获运行时异常: " + e.getClass().getSimpleName() + " -> " + e.getMessage());
            System.out.println("【提示】：运行时异常通常应该通过在编码时加强校验（如 if (b != 0)）来避免！");
        }

        /* ---------------------------------------------------------
         * 2. 编译时异常演练 (编译器强制要求必须处理)
         * --------------------------------------------------------- */
        System.out.println("\n--- 2. 编译时异常 (Checked Exception) 演示 ---");
        // SimpleDateFormat 的 parse() 方法在源码中显式声明了 throws ParseException
        // 因此如果这里不加 try-catch，编译器会直接报错无法编译！
        String dateStr = "2026-11-22";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = sdf.parse(dateStr);
            System.out.println("日期解析成功: " + date);

            // 传入错误格式测试
            String badDateStr = "2026年11月22日";
            sdf.parse(badDateStr); // 格式不匹配，触发 ParseException
        } catch (ParseException e) {
            System.out.println("成功捕获编译时异常: " + e.getClass().getSimpleName() + " (输入与模板格式不匹配)");
            System.out.println("【提示】：编译时异常用于提醒程序员：此外部依赖/输入极其不可控，必须制定后备防御策略！\n");
        }
    }
}
