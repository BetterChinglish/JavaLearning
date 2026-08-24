package com.betterchinglish.demo11_Runtime;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Runtime 类教学示例
 * -------------------------------------------------------------
 * Runtime 在 java.lang 包下，无需 import。
 * 它代表 Java 程序运行时的环境，可以获取内存、CPU 核心数，
 * 甚至能在程序里执行系统命令。
 * 特点：Runtime 是“单例”的，不能 new，要用 Runtime.getRuntime() 获取唯一实例。
 */
public class Test {

    public static void main(String[] args) throws Exception {

        /* =========================================================
         * 1. Runtime.getRuntime() —— 获取运行时对象
         *    作用：拿到 JVM 运行时环境的唯一实例
         *    入参：无
         *    出参：Runtime 实例（单例）
         * ========================================================= */
        Runtime rt = Runtime.getRuntime();


        /* =========================================================
         * 2. availableProcessors() —— 可用 CPU 核心数
         *    作用：返回 JVM 能用的处理器（核心）数量
         *    入参：无   出参：int，核心数
         * ========================================================= */
        int cores = rt.availableProcessors();
        System.out.println("可用 CPU 核心数 = " + cores); // 输出: 如 8（因机器而异）


        /* =========================================================
         * 3. totalMemory() / freeMemory() —— 内存信息
         *    作用：
         *      totalMemory  JVM 当前从系统申请到的总内存（字节）
         *      freeMemory   JVM 中当前空闲的内存（字节）
         *    入参：无   出参：long，字节数
         *    常用：totalMemory - freeMemory 可估算已用内存
         *    注意：数值因环境和时机而异，这里只标注含义
         * ========================================================= */
        long total = rt.totalMemory();
        long free = rt.freeMemory();
        System.out.println("总内存(字节) = " + total);   // 输出: 如 268435456
        System.out.println("空闲内存(字节) = " + free);  // 输出: 如 260000000
        System.out.println("已用内存(字节) = " + (total - free));


        /* =========================================================
         * 4. rt.gc() —— 建议垃圾回收（同 System.gc()）
         *    作用：建议 JVM 回收无用对象
         *    入参：无   出参：无（void）
         * ========================================================= */
        rt.gc();


        /* =========================================================
         * 5. rt.exec(命令) —— 执行系统命令
         *    作用：在操作系统里启动一个新进程执行命令，返回 Process 对象
         *    入参：String 命令（不同系统命令不同！）
         *    出参：Process，可用它读取命令输出、等待结束
         *
         *    ⚠ 平台差异（重要）：
         *      - Windows：要用 "cmd /c 命令"，例如 "cmd /c ver"、"cmd /c dir"
         *      - Linux / macOS：直接用命令，例如 "ls"、"uname -a"
         *    当前是 Windows 环境，下面用 "cmd /c ver" 查看系统版本（只读安全命令）。
         * ========================================================= */
        // 执行命令，返回进程对象（Windows 用 cmd /c 前缀）
        Process p = rt.exec("cmd /c ver");
        // 用字符流读取该进程的标准输出
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(p.getInputStream(), "GBK")); // Windows 中文用 GBK 防乱码
        String line;
        System.out.println("命令(cmd /c ver)输出：");
        while ((line = reader.readLine()) != null) {
            System.out.println(line); // 输出: 如 Microsoft Windows [版本 10.0.19045.XXXX]
        }
        reader.close();
        p.waitFor(); // 等待命令执行完毕（可选）


        /* =========================================================
         * 6. maxMemory() —— JVM 最大可用内存
         *    作用：返回 JVM 最多能向系统申请的内存（字节）
         *    入参：无   出参：long
         * ========================================================= */
        System.out.println("最大内存(字节) = " + rt.maxMemory()); // 输出: 如 4261412864
    }
}
