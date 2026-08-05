package com.betterchinglish.demo10_innerClass;

public class Car {

  String name;
  int limitAge;
  String color;

  int a = 10;

  // 内部类
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

  // 私有内部类
  public Tire getTileInstance(String band, int size) {
    return new Tire(band, size);
  }
  private class Tire {
    String band;
    int size;

    public Tire(String band, int size) {
      this.band = band;
      this.size = size;
    }
  }


  // 静态内部类
  static class StaticInnerClass {
    void show1() {
      System.out.println("show 1");
    }

    static void show2() {
      System.out.println("static show 2");
    }
  }

  // 局部内部类
  public void show() {

    int a = 100;
    class LocalClass {
      String name;
      int age;

      void show () {
        System.out.println("lc show");
        System.out.println(a);
        System.out.println(Car.this.a);
      }
      static void show1() {
        System.out.println("lc static show1");
      }
    }

    LocalClass lc = new LocalClass();
    lc.show();
    LocalClass.show1();
  }


}
