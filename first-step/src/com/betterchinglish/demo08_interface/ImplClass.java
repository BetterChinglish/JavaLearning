package com.betterchinglish.demo08_interface;

// 接口中有重名的方法只覆写一个就行, 接口是一种规则, 两个规则要求都要有sleep方法, 当前类有重写就行
public class ImplClass implements Interface1, Interface2 {

  @Override
  public void swim() {

  }

  @Override
  public void sleep() {

  }

  // drink是默认带方法体的方法, 如果多个接口都有带默认方法体的方法, 则必须重写
  // 当前是一个接口带方法体, 一个不带, 也需要重写
  @Override
  public void drink() {
    System.out.println("确实需要喝水");
  }

  // 私有方法
  // private ReturnType FuncName(params) {}
  // private static ReturnType FuncName(params) {}
}
