package com.betterchinglish.demo20_Exception;

/**
 * 模块八：Throwable / Exception 常用方法详解
 * ----------------------------------------------------------------------
 * 【对应课程】：异常-08-异常中的常见方法
 * 【核心方法三大件】：
 *   1. public String getMessage()
 *      - 作用：返回此 throwable 的详细消息字符串（简要描述异常原因）。
 *      - 场景：适合直接将友好的错误原因展示给前端或普通用户。
 *
 *   2. public String toString()
 *      - 作用：返回此 throwable 的简短描述，格式为：【异常完整全类名: getMessage()】。
 *
 *   3. public void printStackTrace()
 *      - 作用：把异常的错误信息最完整、最彻底地打印在控制台上（包含异常名、原因、整个方法调用链路以及精准行号）。
 *      - 【底层内幕剖析】：
 *        * 它的底层使用的是 System.err.println() 进行输出，而不是普通的 System.out！
 *        * System.err 是 Java 的“标准错误输出流”，在 IDE（如 IntelliJ IDEA）中默认以【鲜红色】字体展示。
 *        * 为什么有时看到红色日志和普通白色日志打印顺序错乱？
 *          因为 System.out 与 System.err 走的是两个不同的独立缓冲区管道，属于异步并发输出，所以可能出现轻微微秒级的交织。
 *        * 生产环境建议：开发调试用 printStackTrace() 极佳；生产线上通常通过日志框架（SLF4J + Logback）使用 log.error("...", e) 归档到磁盘日志文件中。
 */
public class D08_ExceptionCommonMethods {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块八】Throwable 核心方法对比与 printStackTrace 底层细节");
        System.out.println("==================================================");

        int[] arr = {1, 23, 4, 5, 6, 67};

        try {
            System.out.println("尝试触发下标越界: arr[10]");
            System.out.println(arr[10]);
        } catch (ArrayIndexOutOfBoundsException e) {
            /* ---------------------------------------------------------
             * 1. e.getMessage()
             * --------------------------------------------------------- */
            System.out.println("\n--- 1. e.getMessage() ---");
            System.out.println("返回值: " + e.getMessage());
            System.out.println("特点: 只有最精简的错误参数/原因，无类名前缀。");

            /* ---------------------------------------------------------
             * 2. e.toString()
             * --------------------------------------------------------- */
            System.out.println("\n--- 2. e.toString() ---");
            System.out.println("返回值: " + e.toString());
            System.out.println("特点: 包含【异常完整类名】+【冒号】+【getMessage()】。");

            /* ---------------------------------------------------------
             * 3. e.printStackTrace() 及其底层揭秘
             * --------------------------------------------------------- */
            System.out.println("\n--- 3. e.printStackTrace() 打印完整堆栈 ---");
            System.out.println("【说明】：底层通过 System.err 异步输出，红字展示调用栈。");

            e.printStackTrace();

            // 为避免多流并发交织导致教学控制台文字混乱，稍作毫秒级缓冲同步
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }
        }

        System.out.println("\n--- 4. 总结对比速查 ---");
        System.out.println("  - getMessage()      : 用户友好级简述；");
        System.out.println("  - toString()        : 简短说明（类名 + 简述）；");
        System.out.println("  - printStackTrace() : 程序员排错排查必备，最全面但开销最大。\n");
    }
}
