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
    }
}
