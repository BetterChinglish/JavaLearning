package com.betterchinglish.demo14_BigDecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal 类教学示例
 * -------------------------------------------------------------
 * BigDecimal 在 java.math 包下，必须 import java.math.BigDecimal（以及 RoundingMode）。
 * 作用：表示“任意精度”的十进制数，用来做金钱、利率等“不能有一丝误差”的计算。
 * 特点：它是不可变对象，所有运算（add 等）都会返回“新的 BigDecimal”，不会改原来的。
 * 因为不是基本类型，所以不能用 + - * /，要调用方法：add/subtract/multiply/divide。
 *
 * 核心表示模型（务必记住）：
 *   一个 BigDecimal 的值 = unscaledValue × 10^(-scale)
 *   - unscaledValue : 一个 BigInteger，叫“未缩放值 / 整数尾数”
 *   - scale         : 一个 int，表示“小数点向右移动几位才是真实值”（即小数位数）
 *   例如：new BigDecimal("12.34") -> unscaledValue=1234, scale=2 -> 1234 × 10^-2 = 12.34
 *   这正是它能“无精度丢失”的根本原因（底层是整数运算，再记录小数位）。
 */
public class Test {

    public static void main(String[] args) {

        /* =========================================================
         * 0. 为什么不用 double / float 做精确计算
         *    原因：double 用“二进制浮点数”表示小数，很多十进制小数
         *          （如 0.1）在二进制里是无限循环小数，只能近似存储。
         *    后果：0.1 + 0.2 在 double 下不等于 0.3，累加会越偏越多。
         *    解决：用 BigDecimal(String) 来表示，能从“字符串”精确构造。
         * ========================================================= */
        double d1 = 0.1;
        double d2 = 0.2;
        System.out.println("double: 0.1 + 0.2 = " + (d1 + d2)); // 输出: 0.30000000000000004（误差！）
        System.out.println("double 是否等于 0.3: " + ((d1 + d2) == 0.3)); // 输出: false

        BigDecimal b1 = new BigDecimal("0.1");
        BigDecimal b2 = new BigDecimal("0.2");
        System.out.println("BigDecimal: 0.1 + 0.2 = " + b1.add(b2)); // 输出: 0.3（精确）


        /* =========================================================
         * 1. 三种构造方式（重点：前两种安全，第三种有坑）
         *    方式一：new BigDecimal(String)  —— 最推荐，字符串精确构造
         *    方式二：BigDecimal.valueOf(double) —— 推荐，内部先转字符串再构造，安全
         *    方式三：new BigDecimal(double) —— 不推荐！double 本身就不精确，会带入误差
         *    注意：不能直接写 BigDecimal a = 10; 必须传参构造
         * ========================================================= */
        BigDecimal a = new BigDecimal("10.5");          // 方式一：精确
        BigDecimal c = BigDecimal.valueOf(3.14);        // 方式二：安全（推荐用于 double 字面量）
        BigDecimal bad = new BigDecimal(0.1);            // 方式三：有坑！
        System.out.println("new BigDecimal(\"10.5\") = " + a);   // 输出: 10.5
        System.out.println("BigDecimal.valueOf(3.14) = " + c);   // 输出: 3.14
        System.out.println("new BigDecimal(0.1) = " + bad);      // 输出: 0.1000000000000000055511151231257827021181583404541015625（误差！）


        /* =========================================================
         * 2. add(another) —— 加法
         *    作用：当前数 + another
         *    入参：BigDecimal 另一个数   出参：BigDecimal 和（新对象）
         * ========================================================= */
        BigDecimal sum = a.add(c);
        System.out.println("10.5 + 3.14 = " + sum);     // 输出: 13.64


        /* =========================================================
         * 3. subtract(another) —— 减法
         *    作用：当前数 - another
         *    入参：BigDecimal   出参：BigDecimal 差
         * ========================================================= */
        System.out.println("10.5 - 3.14 = " + a.subtract(c)); // 输出: 7.36


        /* =========================================================
         * 4. multiply(another) —— 乘法
         *    作用：当前数 × another
         *    入参：BigDecimal   出参：BigDecimal 积
         *    注意：积的 scale = 两个操作数 scale 之和（小数位数相加）
         * ========================================================= */
        BigDecimal p = new BigDecimal("1.2");   // scale=1
        BigDecimal q = new BigDecimal("1.5");   // scale=1
        System.out.println("1.2 × 1.5 = " + p.multiply(q)); // 输出: 1.80（scale=2）


        /* =========================================================
         * 5. divide(another) —— 除法（BigDecimal 最容易踩坑的地方）
         *    作用：当前数 ÷ another
         *    入参：BigDecimal
         *    出参：BigDecimal 商
         *    坑：如果除不尽（如 1 ÷ 3），不指定舍入会直接抛 ArithmeticException！
         *    正确做法：用 divide(another, scale, roundingMode) 指定保留几位 + 舍入模式。
         * ========================================================= */

        // 5.1 除不尽且不指定舍入 —— 演示异常（用 try-catch 捕获，避免程序崩溃）
        BigDecimal x = new BigDecimal("1");
        BigDecimal y = new BigDecimal("3");
        try {
            System.out.println("1 ÷ 3 = " + x.divide(y)); // 抛异常，不执行到这里
        } catch (ArithmeticException e) {
            System.out.println("1 ÷ 3 不指定舍入会抛异常: " + e.getMessage());
            // 输出: Non-terminating decimal expansion; no exact representable decimal result.
        }

        // 5.2 正确写法：指定保留 2 位小数 + 四舍五入（HALF_UP）
        BigDecimal safe = x.divide(y, 2, RoundingMode.HALF_UP);
        System.out.println("1 ÷ 3（保留2位, 四舍五入） = " + safe); // 输出: 0.33

        // 5.3 能除尽时，不指定舍入也可（但养成写舍入模式的习惯更稳妥）
        System.out.println("10.5 ÷ 3 = " + a.divide(BigDecimal.valueOf(3))); // 输出: 3.5


        /* =========================================================
         * 6. setScale(scale, roundingMode) —— 设置小数位数 + 舍入
         *    作用：把当前数“调整”到指定的小数位数，并按给定模式舍入
         *    入参：int scale（目标小数位数）、RoundingMode 舍入模式
         *    出参：BigDecimal 调整后的值（新对象）
         *    常用 RoundingMode：
         *      HALF_UP    四舍五入（>=0.5 进位）—— 最常用，如金额
         *      HALF_EVEN  银行家舍入（四舍六入五成双）—— 统计学更公平
         *      UP         远离零方向进位（直接进位，不看大小）
         *      DOWN       趋向零方向截断（直接舍去，不进位）
         *      CEILING    向正无穷方向
         *      FLOOR      向负无穷方向
         * ========================================================= */
        BigDecimal v = new BigDecimal("2.345");
        System.out.println("2.345 四舍五入(HALF_UP) 保留2位 = " + v.setScale(2, RoundingMode.HALF_UP));   // 2.35
        System.out.println("2.345 银行家舍入(HALF_EVEN) 保留2位 = " + v.setScale(2, RoundingMode.HALF_EVEN)); // 2.34（5前是4偶数，舍）
        System.out.println("2.345 进位(UP) 保留2位 = " + v.setScale(2, RoundingMode.UP));   // 2.35
        System.out.println("2.345 截断(DOWN) 保留2位 = " + v.setScale(2, RoundingMode.DOWN)); // 2.34


        /* =========================================================
         * 7. compareTo(another) —— 比较大小（推荐）
         *    作用：比较当前数和 another 的数值大小
         *    入参：BigDecimal   出参：int  >0 当前大；=0 相等；<0 当前小
         *    重要：判断“值相等”要用 compareTo == 0，而不要用 equals！
         *          equals 还会比较 scale（小数位数）：
         *          new BigDecimal("1.0").equals(new BigDecimal("1.00")) 是 false
         *          但 compareTo 认为它们相等（值相同）。
         * ========================================================= */
        BigDecimal m = new BigDecimal("1.0");
        BigDecimal n = new BigDecimal("1.00");
        System.out.println("1.0 equals 1.00 = " + m.equals(n));          // 输出: false（scale 不同）
        System.out.println("1.0 compareTo 1.00 = " + m.compareTo(n));    // 输出: 0（值相等）
        System.out.println("10.5 compareTo 3.14 = " + a.compareTo(c));   // 输出: 1（10.5 更大）


        /* =========================================================
         * 8. 与基本类型互转
         *     intValue()    —— 转 int（超出范围会截断，慎用）
         *     longValue()   —— 转 long
         *     doubleValue() —— 转 double（可能重新引入浮点误差，谨慎）
         *     toString()    —— 转字符串（最安全，完整保留）
         * ========================================================= */
        BigDecimal small = new BigDecimal("123.456");
        int i = small.intValue();
        long l = small.longValue();
        double d = small.doubleValue();
        System.out.println("转 int = " + i);     // 输出: 123
        System.out.println("转 long = " + l);    // 输出: 123
        System.out.println("转 double = " + d);  // 输出: 123.456
        System.out.println("转 string = " + small.toString()); // 输出: 123.456


        /* =========================================================
         * 9. 综合例子：金额计算
         *    场景：单价 19.99，买 3 件，再打 8.5 折，结果保留两位小数（四舍五入）。
         * ========================================================= */
        BigDecimal price = new BigDecimal("19.99");
        BigDecimal count = new BigDecimal("3");
        BigDecimal discount = new BigDecimal("0.85");
        BigDecimal total = price.multiply(count).multiply(discount)
                .setScale(2, RoundingMode.HALF_UP);
        System.out.println("3件单价19.99打8.5折 = " + total); // 输出: 50.97


        // 补充：BigDecimal 的存储原理、与 BigInteger 对比、性能说明
        printPrinciples();
    }

    /* =========================================================
     * 补充教学：BigDecimal 的存储原理、与 BigInteger 对比、性能
     *   入参：无   出参：无（void），仅打印说明
     * ========================================================= */
    private static void printPrinciples() {

        System.out.println("\n========== BigDecimal 原理补充 ==========");

        /* ---------------------------------------------------------
         * 1) 存储原理（底层怎么存一个小数）
         *    - 值 = unscaledValue × 10^(-scale)
         *    - unscaledValue 是一个 BigInteger（即“去掉小数点后的整数”）
         *    - scale 是一个 int，表示“小数点后有几位”
         *    - 因为整数部分用 BigInteger 存（任意精度），所以整数位不会溢出；
         *      小数位只是 record 一个 scale，不存在二进制浮点的“近似”问题，
         *      这就是它“无精度丢失”的本质：所有运算最终都回到整数运算。
         *    例：12.34 -> unscaledValue=1234, scale=2 -> 1234 × 10^-2
         *        0.001 -> unscaledValue=1,   scale=3 -> 1 × 10^-3
         * --------------------------------------------------------- */
        System.out.println("【存储原理】");
        System.out.println("  模型：value = unscaledValue × 10^(-scale)");
        System.out.println("    unscaledValue : BigInteger，存去掉小数点后的整数");
        System.out.println("    scale         : int，记录小数点后有几位的“负指数”");
        System.out.println("  例：12.34 -> unscaledValue=1234, scale=2");
        System.out.println("      0.001 -> unscaledValue=1,   scale=3");
        System.out.println("  本质：小数运算最终都转换为整数运算，所以不会像 double 那样丢精度");

        /* ---------------------------------------------------------
         * 2) 与 BigInteger 的对比
         *    - BigInteger：任意精度“整数”，scale 永远是 0。
         *    - BigDecimal：任意精度“十进制小数”，= BigInteger × 10^-scale。
         *    - 关系：BigDecimal 的整数部分底层就是 BigInteger，可理解为
         *      “带小数位的 BigInteger”。做整数运算时两者速度接近；
         *      做小数运算时 BigDecimal 多一层 scale 管理，略慢一点点。
         *    - 选择：要整数且超大 -> BigInteger；要精确小数/金额 -> BigDecimal。
         * --------------------------------------------------------- */
        System.out.println("\n【与 BigInteger 对比】");
        System.out.println("  BigInteger  : 任意精度整数，scale 恒为 0");
        System.out.println("  BigDecimal  : 任意精度小数 = BigInteger × 10^-scale");
        System.out.println("  关系：BigDecimal 的整数部分底层就是 BigInteger");
        System.out.println("  选型：超大整数用 BigInteger；精确金额/小数用 BigDecimal");

        /* ---------------------------------------------------------
         * 3) 性能说明
         *    - BigDecimal 的每个运算都是“对象 + 方法调用 + 整数大数运算”，
         *      比基本类型 double/float 的硬件浮点运算慢得多（数量级差距）。
         *    - 优点换来的是“绝对精确”，适合金钱、计费、税率等不能出错的场景。
         *    - 缺点：不适合海量、对性能极敏感的科学计算（那种场景用 double 更快，
         *      或专门的数值库）。日常业务金额计算，BigDecimal 是首选。
         *    - 小贴士：构造优先用 String / valueOf，避免 double 误差；
         *      divide 务必指定 scale + RoundingMode，否则可能抛异常。
         * --------------------------------------------------------- */
        System.out.println("\n【性能说明】");
        System.out.println("  代价：比 double/float 的硬件浮点运算慢很多（对象+大数运算）");
        System.out.println("  收益：换来绝对精确，适合金钱/计费/税率等不能出错的场景");
        System.out.println("  不适：海量高性能科学计算（用 double 或专用数值库更快）");
        System.out.println("  贴士：用 String/valueOf 构造；divide 必写 scale+RoundingMode");
    }
}
