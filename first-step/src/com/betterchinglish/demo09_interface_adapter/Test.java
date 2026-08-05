package com.betterchinglish.demo09_interface_adapter;

public class Test {
  public static void main(String[] args) {
    // 问题: 当一个接口有很多方法, 但是我只想用到其中一个, 此时又必须重写所有方法, 最终的类会很难阅读
    // 解决: 先使用一个类用于空实现这个接口中的所有方法, 再使用实际的类继承这个类即可, 同时重写需要用到的方法即可
    // 这个中间类我们称为adapter (由于空实现去创建这个类的对象也是没有意义的, 可以将其置为抽象方法)
    // 这个思想称为适配器模式.
    MyClass m1 = new MyClass();
    m1.method3();
  }
}
