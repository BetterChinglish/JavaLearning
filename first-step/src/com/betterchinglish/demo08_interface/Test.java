package com.betterchinglish.demo08_interface;

public class Test {


  public static void main(String[] args) {
    // 接口多态
    ImplClass ii = new ImplClass();
    show(ii);
  }

  // Interface1 i1接受一个对象, 这个对象的类需要实现Interface1即可, 如上main方法中的ii, 即:
  // Interface1 i1 = new ImplClass(); ImplClass需要implements Interface1
  // 也遵循编译看左边, 运行看右边; 编译看左边是看Interface1有没有要调用的方法, 运行看右边是实际去执行的方法其实是对象中(可能重写)的
  static void show(Interface1 i1) {
    i1.drink();
  }

}
