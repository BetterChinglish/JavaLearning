package com.betterchinglish.demo08_interface;

public interface Interface1 {
  // 对于变量 public static final不加默认虚拟机也会加上, 完整写法:
  public static final String name = "zhangsan";
  int age = 20;
  String gender = "boy";

  // 对于方法 public abstract不加默认虚拟机也会加上, 完整写法:
  public abstract void sleep();

  public default void drink() {
    System.out.println("需要喝水");
  }


  // 私有方法
  // private ReturnType FuncName(params) {}
  // private static ReturnType FuncName(params) {}
  // 私有方法服务于默认方法, 如下两个默认方法有公共部分(注释代码), 我们只能新增一个show3并在show1,show2中调用
  public default void show1() {
    // System.out.println("展示内容");
    show3();
    System.out.println("show1执行");
  }
  public default void show2() {
    // System.out.println("展示内容");
    show3();
    System.out.println("show2执行");
  }
  // 但是这个方法只用于内部的show1,show2, 外界调用没有意义, 故而使用私有方法解决这个问题
  // public default void show3() {
  //    System.out.println("展示内容");
  // }
  private void show3() {
    System.out.println("展示内容");
  }

  // 如果show1,show2是静态方法, 静态方法只能使用静态方法, 则使用的私有方法也要改为私有静态方法
  //  private static void show3() {
  //    System.out.println("展示内容");
  //  }
}
