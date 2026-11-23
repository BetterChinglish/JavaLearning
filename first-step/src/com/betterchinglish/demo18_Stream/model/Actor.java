package com.betterchinglish.demo18_Stream.model;

import java.util.Objects;

/**
 * 演员实体类 - 用于 Stream 流综合练习（自定义对象过滤与收集）
 */
public class Actor {

    private String name;
    private int age;
    private String gender; // "男" / "女"

    public Actor() {
    }

    public Actor(String name, int age) {
        this.name = name;
        this.age = age;
        this.gender = "未知";
    }

    public Actor(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return age == actor.age && Objects.equals(name, actor.name) && Objects.equals(gender, actor.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, gender);
    }

    @Override
    public String toString() {
        return "Actor{name='" + name + "', age=" + age + ", gender='" + gender + "'}";
    }
}
