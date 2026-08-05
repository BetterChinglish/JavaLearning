package com.betterchinglish.demo00_helloworld;

import java.sql.Array;
import java.util.Arrays;

public class HelloWorld {
  // test comment
  public static void main(String[] args) {
    // numFunc();

    // arrFunc();

    System.out.println(getSum(10, 20));
    System.out.println(getSum(1,2,3));

    short s1 = 5, s2 = 127;
    System.out.println(getSum(s1, s2));
  }

  // 方法重载： 同一个类下的方法名字相同，参数不同（参数类型、参数个数）
  public static int getSum(int a, int b) {
    System.out.println("int 2 params");
    return a + b;
  }

  public static int getSum(int a, int b, int c) {
    System.out.println("int 3 params");
    return a + b + c;
  }

  public static int getSum(short a, short b) {
    System.out.println("short 2 params");
    return a + b;
  }

  public static void arrFunc() {
    // 静态初始化

    // 完整声明
    int [] myarr1 = new int[] {1,2,3,4};
    System.out.println(Arrays.toString(myarr1));

    // 简化声明
    int [] myarr2 = {1,2,3,5};
    System.out.println(Arrays.toString(myarr2));

    // 直接输出是地址
    System.out.println(myarr1);
    System.out.println(myarr2);


    // 索引访问
    System.out.println(myarr1[3]);
    System.out.println(myarr2[3]);

    System.out.println("------------");

    // 遍历
    for(int i = 0; i < myarr1.length; i++) {
      System.out.println(myarr1[i]);
    }

    System.out.println("------------");

    for(int i = 0; i < myarr2.length; i++) {
      System.out.println(myarr2[i]);
    }

    System.out.println("------------");

    // 求和
    int sum = 0;
    for(int i = 0; i < myarr1.length; i++) {
      sum += myarr1[i];
    }
    System.out.println(sum);

    System.out.println("------------");

    // 动态初始化, 指定长度


    // 整数类型默认0
    // 小数类型 0.0
    // boolean false
    // char \u0000 空格
    // 引用类型 null
    int [] myarr3 = new int[3];
    System.out.println(Arrays.toString(myarr3));

    boolean [] mybooleanarr1 = new boolean[10];
    System.out.println(Arrays.toString(mybooleanarr1));

  }

  public static void numFunc() {
    System.out.println("hello world");
    System.out.println("---------------------");

    int num1 = 1;
    int num2 = 2;

    // 加
    int sum = num1 + num2;
    // 减
    int sub = num1 - num2;
    // 乘
    int multiplied = num1 * num2;
    // 除
    double quotient = (double) num1 / num2;
    // 余数
    int remainder = num1 % num2;

    System.out.println("sum: " + sum);
    System.out.println("sub: " + sub);
    System.out.println("multiplied: " + multiplied);
    System.out.println("quotient: " + quotient);
    System.out.println("remainder: " + remainder);
    System.out.println("---------------------");

    // 隐式转换: 类型不同进行计算时会将小的往大的转
    // byte -> short -> int -> long -> float -> double
    double d1 = 12.3;
    System.out.println(TypeUtils.getTypeName(d1 + num1));


    // byte short char进行运算均会直接先转为int再计算
    byte b1 = 127;
    byte b2 = 2;
    System.out.println(TypeUtils.getTypeName(b1 + b2)); // int

    System.out.println("---------------------");

    // 强制转换：大的数据类型往小的转，类型不同进行计算或赋值时需要手动将数据转换，
    int i1 = (int) d1;
    System.out.println(i1);

    System.out.println("---------------------");


    // 字符串相加，+计算中出现字符串则会变为字符串连接
    String s1 = "nihao";
    System.out.println(TypeUtils.getTypeName(s1 + 123));
    System.out.println(s1 + 123);

  }


}
