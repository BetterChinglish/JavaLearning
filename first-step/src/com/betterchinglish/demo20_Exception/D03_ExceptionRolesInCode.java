package com.betterchinglish.demo20_Exception;

import com.betterchinglish.demo20_Exception.exception.AgeOutOfBoundsException;

/**
 * 模块三：异常在代码中的两个核心作用
 * ----------------------------------------------------------------------
 * 【对应课程】：异常-03-异常在代码中的两个作用
 * 【两大核心作用】：
 *   作用一：【查询 bug 关键信息用于排查定位】
 *          - 异常信息中详细封装了：异常的类型名称、原因简述、出错的类名、方法名以及精准到源码中的【行号】。
 *          - 开发人员顺着调用栈回溯，可以几秒钟内快速定位问题现场，是排查 bug 的“第一手线索”。
 *
 *   作用二：【作为方法的特殊“返回值”打断流程，向调用者传递错误】
 *          - 在传统面向过程编程中，错误通常靠返回 -1、null 或 false；但这会导致正常业务数据与错误标记混淆。
 *          - 异常机制提供了一条“专用的错误告警通道”：当方法遇到无法解决的非法数据或逻辑冲突时，
 *            通过抛出异常立即强行中断后续代码的执行，保护业务数据的正确性，同时将具体失败原因准确送达调用者。
 */
public class D03_ExceptionRolesInCode {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块三】异常在代码中的两大核心作用");
        System.out.println("==================================================");

        /* ---------------------------------------------------------
         * 作用一：排查定位 Bug 的精准线索
         * --------------------------------------------------------- */
        System.out.println("--- 作用一：查询 Bug 关键信息用于排查定位 ---");
        try {
            String str = null;
            System.out.println(str.length()); // 刻意触发空指针异常
        } catch (NullPointerException e) {
            System.out.println("1. 捕获到异常类型: " + e.getClass().getName());
            StackTraceElement element = e.getStackTrace()[0];
            System.out.println("2. 出错文件: " + element.getFileName());
            System.out.println("3. 出错类与方法: " + element.getClassName() + "." + element.getMethodName());
            System.out.println("4. 出错精准行号: 第 " + element.getLineNumber() + " 行");
            System.out.println("【收益】：无需盲目猜测，精准还原案发现场！");
        }

        /* ---------------------------------------------------------
         * 作用二：作为方法的特殊“返回值”打断非法流程，向上汇报
         * --------------------------------------------------------- */
        System.out.println("\n--- 作用二：阻断非法业务执行，作为特殊通道通知调用者 ---");
        try {
            System.out.println("准备录入人员年龄: 150 岁...");
            registerUser("张三", 150);
            System.out.println("注册成功！"); // 如果上方抛出异常，此行绝对不会被执行，保证了脏数据不会入库！
        } catch (AgeOutOfBoundsException e) {
            System.out.println("接收到底层业务阻断告警: " + e.getMessage());
            System.out.println("上层调用者可以据此采取补救措施（如提示前端重新输入）！\n");
        }
    }

    /**
     * 模拟用户注册方法：若年龄超出 0~120 范围，抛出异常阻断业务
     */
    private static void registerUser(String name, int age) {
        if (age < 0 || age > 120) {
            // 通过 throw 抛出异常，立即强行结束该方法，后方任何代码都不会执行
            throw new AgeOutOfBoundsException("年龄不合法！输入值为: " + age + "，合法范围为 [0, 120]");
        }
        System.out.println("用户信息校验通过: [姓名=" + name + ", 年龄=" + age + "]");
    }
}
