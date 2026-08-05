package com.betterchinglish.demo10_innerClass;

public class Test {
  public static void main(String[] args) {

    Car.Engine engine = new Car().new Engine();
    engine.show();

  }
}
