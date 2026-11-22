package com.betterchinglish.demo20_Exception;

/**
 * 模块六：捕获异常灵魂四问（前两问：灵魂一问 & 灵魂二问）
 * ----------------------------------------------------------------------
 * 【对应课程】：异常-06-捕获异常灵魂四问（①②）
 *
 * 【灵魂一问】：如果 try 中没有遇到任何问题，怎么执行？
 *   答：会把 try 里面的所有代码全部自上而下顺序执行完毕，
 *       完全【不会】进入并执行 catch 代码块中的任何语句！
 *
 * 【灵魂二问】：如果 try 中可能会遇到多个不同类型的异常，怎么执行？
 *   答：可以为同一个 try 块编写多个对应的 catch 块分别进行精准捕获。
 *       【核心铁律】：
 *       1. 如果多个 catch 捕获的异常类之间存在父子继承关系，
 *          必须保证【子类异常写在上面，父类异常写在下面】！
 *          （若父类写在上方，由于多态机制，父类会直接拦截所有子类异常，导致下方的子类 catch 成为永远无法到达的死代码，Java 编译器直接报编译错误！）
 *       2. JDK 7+ 语法糖：当多个同级子类异常的处理逻辑完全一致时，
 *          可以使用管道符 `|` 合并在同一个 catch 中：`catch (NullPointerException | ArrayIndexOutOfBoundsException e)`。
 */
public class D06_TryCatchSoulQuestionsPart1 {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块六】捕获异常灵魂四问（第①问 与 第②问）");
        System.out.println("==================================================");

        /* ---------------------------------------------------------
         * 【灵魂一问实测】：如果 try 中一切正常，没有遇到问题怎么执行？
         * --------------------------------------------------------- */
        System.out.println("--- 灵魂一问：try 中无异常时的执行路线 ---");
        try {
            System.out.println("步骤 1：执行正常计算 10 + 20 = " + (10 + 20));
            System.out.println("步骤 2：读取配置文件成功");
            System.out.println("步骤 3：try 内部所有语句全部顺利执行完毕！");
        } catch (Exception e) {
            System.out.println("【警告】：只要 try 没有报错，这里绝不可能执行！");
        }
        System.out.println("【灵魂一问结论】：try 全部执行完毕，完全跳过 catch，继续往下跑。\n");

        /* ---------------------------------------------------------
         * 【灵魂二问实测】：如果 try 中可能抛出多个异常，怎么编写多个 catch？
         * --------------------------------------------------------- */
        System.out.println("--- 灵魂二问：多个不同异常的捕获与顺序规则 ---");
        int[] arr = {10, 20, 30};

        for (int i = 0; i < 2; i++) {
            try {
                if (i == 0) {
                    System.out.println("【场景 A 测试】：触发算术异常 (10 / 0)...");
                    int res = 10 / 0;
                } else {
                    System.out.println("【场景 B 测试】：触发数组下标越界 (arr[5])...");
                    int val = arr[5];
                }
            } catch (ArithmeticException e) {
                // 子类异常 1：专门处理算术异常
                System.out.println("--> 命中专项处理分支：捕获到算术除零异常: " + e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                // 子类异常 2：专门处理数组越界异常
                System.out.println("--> 命中专项处理分支：捕获到数组越界异常: " + e.getMessage());
            } catch (Exception e) {
                // 终极父类异常：必须放在最后！充当最后一道安全网兜底
                System.out.println("--> 命中通用兜底分支：捕获到未知异常: " + e.getMessage());
            }
        }

        // JDK 7+ 管道符多异常联合捕获
        System.out.println("\n【进阶技巧】JDK 7+ 管道语法糖合并处理：");
        try {
            String s = null;
            s.length();
        } catch (NullPointerException | ArithmeticException e) {
            System.out.println("使用管道符捕获到: " + e.getClass().getSimpleName());
        }
        System.out.println();
    }
}
