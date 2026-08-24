package com.betterchinglish.demo12_Object;

/**
 * 学生类：用来演示 Object 的几个核心方法
 * -------------------------------------------------------------
 * 所有 Java 类都默认继承自 Object（不用写 extends 也自动继承）。
 * 这里重写 toString / equals / hashCode，并实现 Cloneable 接口，
 * 这样才能演示 Object.clone() 做对象拷贝。
 */
public class Student implements Cloneable {

    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    /* ---------------------------------------------------------
     * 重写 toString() —— 自定义对象打印内容
     *   作用：对象被 System.out.println 打印时，默认会调用 toString()
     *   入参：无   出参：String，对象的文本描述
     *   不重写的话默认输出：类名@哈希码（不好看）
     * --------------------------------------------------------- */
    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + "}";
    }

    /* ---------------------------------------------------------
     * 重写 equals() —— 自定义“两个对象是否相等”的规则
     *   作用：比较当前对象和另一个对象内容是否相同
     *   入参：Object obj，要比较的对象
     *   出参：boolean，true 表示“我们认为相等”
     *   注意：== 比的是地址，equals 比的是内容（这里按 name+age 比）
     * --------------------------------------------------------- */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;          // 同一个对象
        if (obj == null || getClass() != obj.getClass()) return false;
        Student other = (Student) obj;
        return age == other.age && name.equals(other.name);
    }

    /* ---------------------------------------------------------
     * 重写 hashCode() —— 配合 equals 使用
     *   规则：equals 为 true 的两个对象，hashCode 必须相等
     *   入参：无   出参：int，哈希码
     * --------------------------------------------------------- */
    @Override
    public int hashCode() {
        return name.hashCode() + age;
    }

    /* ---------------------------------------------------------
     * 重写 clone() —— 支持对象拷贝
     *   作用：创建并返回当前对象的一个“复制体”
     *   入参：无   出参：Object（需强转成 Student）
     *   前提：类必须实现 Cloneable 接口，否则会抛 CloneNotSupportedException
     *   这里是“浅拷贝”：基本类型和 String 会被复制成独立值
     * --------------------------------------------------------- */
    @Override
    public Student clone() {
        try {
            return (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
