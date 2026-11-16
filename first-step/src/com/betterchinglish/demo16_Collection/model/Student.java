package com.betterchinglish.demo16_Collection.model;

import java.util.Objects;

/**
 * 教学辅助实体类：学生模型
 * -------------------------------------------------------------
 * 用于演示：
 * 1. HashSet / HashMap 去重依赖的 equals() 与 hashCode() 契约
 * 2. TreeSet 自然排序必须实现的 Comparable<Student> 接口
 */
public class Student implements Comparable<Student> {

    private int id;
    private String name;
    private int score;

    public Student() {
    }

    public Student(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * 实现 Comparable 接口的 compareTo 方法
     * 定义对象的“自然排序”规则：按分数降序排列；分数相同时按学号升序排列
     */
    @Override
    public int compareTo(Student o) {
        // 分数降序
        int scoreCompare = Integer.compare(o.score, this.score);
        if (scoreCompare != 0) {
            return scoreCompare;
        }
        // 分数相同则按学号升序
        return Integer.compare(this.id, o.id);
    }

    /**
     * 重写 equals 方法：只要 id 和 name 相同，即判定为同一个学生
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(name, student.name);
    }

    /**
     * 重写 hashCode 方法：依据 id 和 name 计算哈希值
     * 关键契约：如果两个对象 equals 相等，它们的 hashCode 必须相同！
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
                ", score=" + score +
                '}';
    }
}
