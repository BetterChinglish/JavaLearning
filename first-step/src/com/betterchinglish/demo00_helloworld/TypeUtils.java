package com.betterchinglish.demo00_helloworld;

public class TypeUtils {
    /**
     * 通用获取类型名称（处理 null、数组、基本类型、普通对象）
     */
    public static String getTypeName(Object obj) {
        if (obj == null) {
            return "null"; // 或者返回 "null type"
        }

        Class<?> clazz = obj.getClass();

        // 1. 处理数组（如 int[]、String[]）
        if (clazz.isArray()) {
            Class<?> component = clazz.getComponentType();
            return component.getName() + "[]";
        }

        // 2. 处理基本类型的包装类（返回 int, double 而非 Integer, Double）
        if (clazz.isPrimitive()) {
            return clazz.getName();
        }

        // 3. 针对包装类特殊处理，让它输出基本类型名称
        if (obj instanceof Integer) return "int";
        if (obj instanceof Long) return "long";
        if (obj instanceof Double) return "double";
        if (obj instanceof Boolean) return "boolean";
        // ... 其他包装类同理

        // 4. 普通对象，返回类全名
        return clazz.getName();
    }

    /**
     * 通用判断：两个对象类型是否一致（严格匹配，不考虑继承）
     */
    public static boolean isSameType(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null) return false;
        return obj1.getClass() == obj2.getClass();
    }
}

