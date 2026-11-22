package com.betterchinglish.demo20_Exception;

/**
 * 模块七：捕获异常灵魂四问（后两问：灵魂三问 & 灵魂四问 + finally）
 * ----------------------------------------------------------------------
 * 【对应课程】：异常-07-捕获异常灵魂四问（③④）
 *
 * 【灵魂三问】：如果 try 中遇到的问题没有被捕获，怎么执行？
 *   答：相当于 try...catch 针对该异常“白写了”！
 *       因为没有匹配的 catch 能接住这个异常，当前异常依然会层层向外抛出，最终交给【JVM 虚拟机处理】。
 *       结果依然是：控制台以红字打印异常调用栈，并直接中断停止整个程序的运行。
 *
 * 【灵魂四问】：如果 try 中遇到了问题，那么 try 内部下面的其他代码还会执行吗？
 *   答：【绝对不会执行】！
 *       只要 try 中某一行代码抛出异常，Java 会立即在这一行打断正常的指令流，
 *       瞬间生成异常对象并跳出 try 块，寻找与之匹配的 catch 块。
 *       try 块中写在异常代码下方的任何代码都不会被执行！
 *
 * 【补充必知】：finally 语句块的执行保障
 *   - 无论 try 中是否发生异常，也无论异常是否被成功捕获，finally 块中的代码【几乎必定会被执行】！
 *   - 专用于各类宝贵系统资源（IO流、数据库连接、网络套接字 Socket）的关闭与释放。
 */
public class D07_TryCatchSoulQuestionsPart2 {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块七】捕获异常灵魂四问（第③问 与 第④问）");
        System.out.println("==================================================");

        /* ---------------------------------------------------------
         * 【灵魂四问实测】：try 中出问题后，其下方的代码还会执行吗？
         * --------------------------------------------------------- */
        System.out.println("--- 灵魂四问：try 中出现问题时内部后续代码的生死 ---");
        try {
            System.out.println("指令 1：开始执行业务...");
            int result = 10 / 0; // 此行直接爆雷！
            System.out.println("指令 2：计算完毕 (这行绝对不可能被执行！)");
            System.out.println("指令 3：写入数据库 (若执行将导致脏数据，所幸被异常瞬间阻断)");
        } catch (ArithmeticException e) {
            System.out.println("--> 成功跳入 catch：拦截到算术异常，及时规避了指令 2 和 3 的错误执行！");
        }
        System.out.println("【灵魂四问结论】：try 内部报错行之后的代码立即废止，直接跳入 catch！\n");

        /* ---------------------------------------------------------
         * 【灵魂三问实测】：try 中遇到的异常类型与 catch 完全不匹配
         * --------------------------------------------------------- */
        System.out.println("--- 灵魂三问：catch 类型不匹配时的下场 ---");
        System.out.println("例如：代码发生了 NullPointerException，但 catch 只写了 ArrayIndexOutOfBoundsException。");

        // 在子线程中模拟不匹配场景，以防中断主控制台演示
        Thread unhandledThread = new Thread(() -> {
            try {
                String nullStr = null;
                nullStr.length(); // 发生 NullPointerException
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("此 catch 与空指针不匹配，永远进不来！");
            }
        });
        unhandledThread.start();
        try {
            unhandledThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("【灵魂三问结论】：无匹配 catch 相当于没捕获，最终依然交给 JVM 打印红字并终止线程！\n");

        /* ---------------------------------------------------------
         * 【延伸拓展】：finally 代码块的坚定执行
         * --------------------------------------------------------- */
        System.out.println("--- 延伸拓展：finally 绝对执行保障 ---");
        try {
            System.out.println("try 申请系统资源...");
            int n = 5 / 0;
        } catch (ArithmeticException e) {
            System.out.println("catch 正在进行错误记录...");
        } finally {
            System.out.println("finally 执行：无论成败，这里的资源释放代码（如 close()）一定会坚决执行！\n");
        }
    }
}
