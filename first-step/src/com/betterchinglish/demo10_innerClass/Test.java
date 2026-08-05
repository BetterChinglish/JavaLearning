package com.betterchinglish.demo10_innerClass;

public class Test {
  public static void main(String[] args) {

    innerClass();

  }

  public static void innerClass() {
    // 内部类创建与获取其对象
    Car.Engine engine = new Car().new Engine();
    engine.show();

    // 私有内部类获取其对象
    Object tire = new Car().getTileInstance("米其林", 27);
    System.out.println(tire);


    // 静态内部类获取其对象
    Car.StaticInnerClass sic = new Car.StaticInnerClass();
    sic.show1();
    Car.StaticInnerClass.show2();


    System.out.println("---------局部内部类---------");
    // 局部内部类
    new Car().show();
  }
}
