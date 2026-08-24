package com.betterchinglish.demo13_BigInteger;

import java.math.BigInteger;

/**
 * BigInteger 类教学示例
 * -------------------------------------------------------------
 * BigInteger 在 java.math 包下，必须 import java.math.BigInteger。
 * 作用：表示“任意精度”的整数，能存下比 long（最大值约 9.2×10¹⁸）还大得多的数。
 * 特点：它是不可变对象，所有运算（add 等）都会返回“新的 BigInteger”，不会改原来的。
 * 因为不是基本类型，所以不能用 + - * /，要调用方法：add/subtract/multiply/divide。
 */
public class Test {

    public static void main(String[] args) {

        /* =========================================================
         * 1. 构造 BigInteger
         *    方式一：BigInteger(String) 用字符串构造（最常用，避免精度丢失）
         *    方式二：BigInteger.valueOf(long) 用 long 值构造（更方便）
         *    注意：不能直接写 BigInteger a = 10; 必须传参构造
         * ========================================================= */
        BigInteger a = new BigInteger("1000000000000000000000000000000"); // 很大
        BigInteger b = BigInteger.valueOf(20);   // 用 long 构造
        System.out.println("a = " + a);          // 输出: 1000000000000000000000000000000
        System.out.println("b = " + b);          // 输出: 20


        /* =========================================================
         * 2. add(another) —— 加法
         *    作用：当前数 + another
         *    入参：BigInteger 另一个数
         *    出参：BigInteger 两数之和（新对象）
         * ========================================================= */
        BigInteger sum = a.add(b);
        System.out.println("a + b = " + sum);    // 输出: 1000000000000000000000000000020


        /* =========================================================
         * 3. subtract(another) —— 减法
         *    作用：当前数 - another
         *    入参：BigInteger   出参：BigInteger 差
         * ========================================================= */
        BigInteger c = new BigInteger("30");
        System.out.println("b - c = " + b.subtract(c)); // 输出: -10


        /* =========================================================
         * 4. multiply(another) —— 乘法
         *    作用：当前数 × another
         *    入参：BigInteger   出参：BigInteger 积
         * ========================================================= */
        System.out.println("b * c = " + b.multiply(c)); // 输出: 600


        /* =========================================================
         * 5. divide(another) —— 除法（整除，向下取整，不保留小数）
         *    作用：当前数 ÷ another
         *    入参：BigInteger（不能为 0，否则抛异常）
         *    出参：BigInteger 商
         *    想要余数请用 remainder / mod
         * ========================================================= */
        System.out.println("c / b = " + c.divide(b));   // 输出: 1  （30 ÷ 20 = 1 余 10）


        /* =========================================================
         * 6. mod(another) / remainder(another) —— 取余
         *    作用：返回除法的余数
         *    入参：BigInteger   出参：BigInteger 余数
         *    区别：mod 结果符号与除数相同；remainder 与被除数相同（日常用 mod 即可）
         * ========================================================= */
        System.out.println("c % b = " + c.mod(b));      // 输出: 10


        /* =========================================================
         * 7. pow(n) —— 求幂
         *    作用：当前数的 n 次方
         *    入参：int n（指数）
         *    出参：BigInteger
         * ========================================================= */
        System.out.println("2 的 10 次方 = " + BigInteger.valueOf(2).pow(10)); // 输出: 1024


        /* =========================================================
         * 8. gcd(another) —— 最大公约数
         *    作用：求两个数的最大公约数
         *    入参：BigInteger   出参：BigInteger
         * ========================================================= */
        BigInteger x = new BigInteger("12");
        BigInteger y = new BigInteger("18");
        System.out.println("12 和 18 的最大公约数 = " + x.gcd(y)); // 输出: 6


        /* =========================================================
         * 9. compareTo(another) —— 比较大小
         *    作用：比较当前数和 another
         *    入参：BigInteger
         *    出参：int   >0 表示当前数大；=0 相等；<0 当前数小
         * ========================================================= */
        System.out.println("a.compareTo(b) = " + a.compareTo(b)); // 输出: 1 （a 远大于 b）
        System.out.println("b.compareTo(c) = " + b.compareTo(c)); // 输出: -1（b<c）


        /* =========================================================
         * 10. 与基本类型互转
         *     intValue()  —— 转成 int（若超出 int 范围会截断，慎用）
         *     longValue() —— 转成 long
         *     doubleValue() / toString() 也常用
         *     入参：无   出参：对应基本类型
         * ========================================================= */
        BigInteger small = BigInteger.valueOf(123);
        int i = small.intValue();
        long l = small.longValue();
        System.out.println("转 int = " + i);   // 输出: 123
        System.out.println("转 long = " + l);  // 输出: 123


        /* =========================================================
         * 综合例子：计算 50 的阶乘（普通 long 早就溢出了，BigInteger 轻松胜任）
         * ========================================================= */
        BigInteger fact = BigInteger.ONE; // 1
        for (int n = 1; n <= 50; n++) {
            fact = fact.multiply(BigInteger.valueOf(n));
        }
        System.out.println("50! = " + fact);
        // 输出: 30414093201713378043612608166064768844377641568960512000000000000

        // 补充：BigInteger 的存储原理、计算原理与存放范围
        printPrinciples();
    }

    /* =========================================================
     * 补充教学：BigInteger 的存储原理、计算原理、最大值
     *   入参：无   出参：无（void），仅打印说明
     * ========================================================= */
    private static void printPrinciples() {

        System.out.println("\n========== BigInteger 原理补充 ==========");

        /* ---------------------------------------------------------
         * 1) 存储原理（底层怎么存一个数）
         *    - 内部用 一个 int 数组 mag[] 来存“绝对值的二进制位”。
         *    - 每个数组元素是一个 32 位 int，但只当作“无符号的 32 位片段”来用，
         *      即每 32 个二进制位切成一段，依次放进 mag[0], mag[1], ...
         *      例如：一个数 = mag[0]*2^0 + mag[1]*2^32 + mag[2]*2^64 + ...
         *    - 用一个单独的 int 字段 signum 记录“符号”：
         *         -1 表示负数，0 表示 0，+1 表示正数。
         *    - 因为数组长度理论上只受“内存大小”限制，所以可以表示任意大的整数，
         *      这也是它叫“Big”的原因（不像 long 只有固定 64 位）。
         * --------------------------------------------------------- */
        System.out.println("【存储原理】");
        System.out.println("  内部字段示例(概念)：");
        System.out.println("    int[] mag  —— 存放绝对值，按每 32 位一段拆分存储");
        System.out.println("    int signum —— 记录符号：-1 负 / 0 零 / +1 正");
        System.out.println("  例：数字 2^64 + 5 会被拆成 mag[0]=5, mag[1]=1（高位在后）");

        /* ---------------------------------------------------------
         * 2) 计算原理（加减乘除是怎么算的）
         *    - 不可变：每个运算都不改原对象，而是生成“新的 BigInteger”返回。
         *    - 加法/减法：像小学竖式一样，从低位(数组末尾)到高位逐段相加/相减，
         *      每一位可能产生“进位 / 借位”，并往前一段传递。
         *    - 乘法：类似“竖式乘法”，把两段 32 位数相乘（通常用 64 位中间结果
         *      避免溢出），再累加到结果对应的位置上。
         *    - 除法/取余：用“Knuth 算法 D”（试商法）逐位求商和余数，较耗时。
         *    - 因为这些是“任意精度算法”，位数越多越慢，比基本类型的加减乘除慢很多。
         * --------------------------------------------------------- */
        System.out.println("\n【计算原理】");
        System.out.println("  不可变：add/sub... 都返回新对象，原对象不变");
        System.out.println("  加法 = 从低位到高位逐段相加，处理进位");
        System.out.println("  减法 = 从低位到高位逐段相减，处理借位");
        System.out.println("  乘法 = 类似竖式，分段相乘后累加（64 位中间结果防溢出）");
        System.out.println("  除法 = Knuth 算法 D 试商，逐位求商与余数");
        System.out.println("  代价：位数越多越慢，远慢于基本类型运算");

        /* ---------------------------------------------------------
         * 3) 能存放的最大值（理论 & 实际）
         *    - 理论最大值：受“数组长度上限”和“内存”限制，没有固定上限。
         *      数组下标用 int 索引，mag.length 最多约 2^31-1 段，
         *      每段 32 位，所以理论位数上限约 2^31 * 32 ≈ 6.87×10^10 个二进制位，
         *      换算成十进制位数约 2×10^10 位（两千多亿位），但实际受内存限制。
         *    - 实际最大值：取决于 JVM 可用内存（数组能开多大）。
         *      日常可以轻松存下“几千位、几万位”的整数，远超 long（64 位）。
         *    - 对比：long 最大值 = 2^63-1 ≈ 9.22×10^18（仅 19 位十进制）。
         *    - 演示：构造一个 1000 位的整数（由 1000 个 '1' 组成），看它有多少位。
         * --------------------------------------------------------- */
        System.out.println("\n【存放的最大值】");
        System.out.println("  理论：受数组长度(int 索引) + 内存限制，无固定上限");
        System.out.println("        理论位数上限 ≈ 2^31 * 32 个二进制位 ≈ 2×10^10 十进制位");
        System.out.println("  实际：取决于 JVM 内存，轻松存下数万位的大整数");
        System.out.println("  对比 long 最大值 = 2^63-1 ≈ 9.22×10^18（只有 19 位十进制）");

        // 演示：用 1000 个 '1' 组成字符串，构造一个 1000 位的 BigInteger
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < 1000; k++) {
            sb.append('1');
        }
        BigInteger huge = new BigInteger(sb.toString());
        System.out.println("  演示：构造一个由 1000 个 '1' 组成的数，它的十进制位数 = "
                + huge.toString().length()); // 输出: 1000（说明 BigInt 轻松存下 1000 位）

        // 演示：它已经远超 long 的范围（long 最多 19 位）
        System.out.println("  这个数远超 long 的 19 位上限，longValue() 会因溢出而失真：");
        System.out.println("    huge.longValue() = " + huge.longValue());
        // 输出: 一个被截断的错误值（long 放不下 1000 位，所以不要用 longValue 转超大数）
    }
}
