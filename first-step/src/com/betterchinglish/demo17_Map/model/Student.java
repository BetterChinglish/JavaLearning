package com.betterchinglish.demo17_Map.model;

import java.util.Objects;

/**
 * 辅助实体类：学生模型
 * -------------------------------------------------------------
 * 用于演示：
 * 1. 作为 HashMap 的 Key 时，必须正确重写 equals() 和 hashCode() 保证去重与桶定位。
 * 2. 作为 TreeMap 的 Key 时，必须实现 Comparable 接口以支持自然排序。
 */
public class Student implements Comparable<Student> {

    private int id;
    private String name;
    private int age;

    public Student() {
    }

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 作为 TreeMap 的 Key 时的“自然排序规则”：
     * 优先按年龄升序排列；年龄相同按学号升序排列
     */
    @Override
    public int compareTo(Student o) {
        int ageCompare = Integer.compare(this.age, o.age);
        if (ageCompare != 0) {
            return ageCompare;
        }
        return Integer.compare(this.id, o.id);
    }

    /**
     * 重写 equals 方法：只要 id 和 name 相同即判定为同一学生
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(name, student.name);
    }

    /**
     * 重写 hashCode 方法：与 equals 严格对应，保证作为 HashMap Key 时的哈希寻址一致性
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
