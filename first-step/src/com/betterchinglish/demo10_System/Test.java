package com.betterchinglish.demo10_System;

/**
 * System 类教学示例
 * -------------------------------------------------------------
 * System 在 java.lang 包下，无需 import。
 * 它提供和系统相关的功能：标准输入输出、数组拷贝、
 * 时间毫秒、系统属性、退出程序、垃圾回收提示等。
 * 里面大多是静态方法，直接用 System.xxx() 调用。
 */
public class Test {

    public static void main(String[] args) {

        /* =========================================================
         * 1. System.arraycopy(源, 源起点, 目标, 目标起点, 长度)
         *    作用：把数组的一部分快速复制到另一个数组（native 实现，效率高）
         *    入参：
         *       src      源数组
         *       srcPos   从源数组第几个位置开始复制
         *       dest     目标数组
         *       destPos  粘贴到目标数组的第几个位置
         *       length   要复制的元素个数
         *    出参：无（void），结果直接写入 dest 数组
         * ========================================================= */
        int[] src = {1, 2, 3, 4, 5};
        int[] dest = new int[5];
        System.arraycopy(src, 0, dest, 0, src.length);
        System.out.println("arraycopy 后 dest = ");
        for (int i : dest) {
            System.out.print(i + " ");   // 输出: 1 2 3 4 5
        }
        System.out.println();

        // 只复制中间一段：把 src 的第 2~3 个(下标1,2) 复制到 dest 下标3开始
        int[] dest2 = new int[5];
        System.arraycopy(src, 1, dest2, 3, 2);
        System.out.print("部分复制 dest2 = ");
        for (int i : dest2) {
            System.out.print(i + " ");   // 输出: 0 0 0 2 3
        }
        System.out.println();


        /* =========================================================
         * 2. System.currentTimeMillis() —— 当前时间（毫秒）
         *    作用：返回从 1970-01-01 00:00:00 UTC 到现在的毫秒数
         *    入参：无
         *    出参：long，毫秒时间戳
         *    常用：统计代码运行耗时
         *    注意：绝对值每次运行都不同，这里只标注含义
         * ========================================================= */
        long start = System.currentTimeMillis();
        // 模拟一段耗时操作
        int sum = 0;
        for (int i = 0; i < 1000000; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("循环耗时(毫秒) = " + (end - start)); // 输出: 一个很小的整数(如 2)


        /* =========================================================
         * 3. System.getProperty(键) —— 读取系统属性
         *    作用：获取 JVM / 操作系统相关的配置信息
         *    入参：String 类型的属性名（如 "java.version"、"os.name"）
         *    出参：String，对应属性的值；不存在则返回 null
         *    常用键：java.version(版本)、os.name(系统名)、
         *            user.name(用户名)、user.dir(工作目录)
         * ========================================================= */
        System.out.println("Java 版本 = " + System.getProperty("java.version"));
        System.out.println("操作系统 = " + System.getProperty("os.name"));     // 如 Windows 10
        System.out.println("当前用户 = " + System.getProperty("user.name"));
        System.out.println("工作目录 = " + System.getProperty("user.dir"));


        /* =========================================================
         * 4. System.gc() —— 建议 JVM 进行垃圾回收
         *    作用：给垃圾回收器一个“建议”，尽快回收无用对象
         *    入参：无   出参：无（void）
         *    注意：只是“建议”，JVM 不一定立刻执行，不能依赖它释放资源
         * ========================================================= */
        System.gc();  // 仅作演示，实际回收时机由 JVM 决定


        /* =========================================================
         * 5. System.exit(状态码) —— 终止当前 Java 程序
         *    作用：立即结束 JVM；0 表示正常退出，非 0 表示异常退出
         *    入参：int 状态码
         *    出参：无（程序直接结束，后面代码不会再执行）
         *    注意：这里注释说明，不实际调用，否则下面代码跑不到。
         *    若取消下一行注释运行，程序会在此处直接结束。
         * ========================================================= */
        // System.exit(0);

        System.out.println("程序正常执行到最后（说明没调用 exit）");
        // 输出: 程序正常执行到最后（说明没调用 exit）
    }
}
