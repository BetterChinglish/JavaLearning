package com.betterchinglish.demo10_innerClass;

public class Car {

  String name;
  int limitAge;
  String color;

  int a = 10;

  // String engineName;
  // int engineLimitAge;
  // 引擎可以使用单独的类来表示
  class Engine {
    String engineName;
    int engineLimitAge;

    int a = 20;

    public void show() {
      int a = 30;
      System.out.println(a);
      System.out.println(this.a);
      System.out.println(Car.this.a);
    }
  }


}
