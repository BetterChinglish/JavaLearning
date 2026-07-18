package com.betterchinglish.demo00_helloworld;

public class HelloWorld {
    // test comment
    public static void main(String[] args) {
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
