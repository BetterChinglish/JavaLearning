package com.betterchinglish.demo11_annoymousInnerClass;

public class Test {
  public static void main(String[] args) {

    // 使用匿名类实现接口并创建对象
    Interface1 i1 = new Interface1() {
      @Override
      public void swim() {
        System.out.println("匿名类重写方法swim");
      }
    };

    // 使用匿名类继承Animal类并创建对象
    Animal a = new Animal() {
      @Override
      public void drink() {
        System.out.println("舌头卷起水来喝");
      }
    };
  }
}
