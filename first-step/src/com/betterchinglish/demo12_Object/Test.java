package com.betterchinglish.demo12_Object;

/**
 * Object 类方法教学示例
 * -------------------------------------------------------------
 * Object 是所有 Java 类的“老祖宗”，里面定义了一些所有对象都能用的方法。
 * 常用：toString、equals、hashCode、getClass、clone。
 * （finalize 已废弃，仅作说明，不演示调用。）
 */
public class Test {

    public static void main(String[] args) {

        Student s1 = new Student("张三", 18);
        Student s2 = new Student("张三", 18);
        Student s3 = new Student("李四", 20);


        /* =========================================================
         * 1. toString() —— 把对象转成字符串
         *    作用：打印对象 / 拼接字符串时自动调用
         *    入参：无   出参：String
         *    我们在 Student 里重写了，所以打印的是友好内容
         * ========================================================= */
        System.out.println(s1.toString()); // 输出: Student{name='张三', age=18}
        System.out.println(s1);            // 输出同上（println 内部会自动调用 toString）


        /* =========================================================
         * 2. equals(Object) —— 比较两个对象是否“相等”
         *    作用：判断内容是否相同
         *    入参：Object 另一个对象
         *    出参：boolean
         *    对比 == ：== 比较地址，equals(重写后) 比较内容
         * ========================================================= */
        System.out.println(s1.equals(s2)); // 输出: true  （内容相同）
        System.out.println(s1.equals(s3)); // 输出: false （名字/年龄不同）


        /* =========================================================
         * 3. hashCode() —— 哈希码
         *    作用：返回对象的一个整数“指纹”
         *    入参：无   出参：int
         *    规则：equals 相等的对象，hashCode 必须相等
         *    具体数值每次运行可能不同，这里只标注相等关系
         * ========================================================= */
        System.out.println("s1.hashCode = " + s1.hashCode());
        System.out.println("s2.hashCode = " + s2.hashCode()); // 与 s1 相等
        System.out.println("s3.hashCode = " + s3.hashCode()); // 与上面不同


        /* =========================================================
         * 4. getClass() —— 获取运行时类对象
         *    作用：返回对象“实际所属的类”的 Class 对象
         *    入参：无   出参：Class<?>
         *    常用：通过 .getName() 拿类名，或用 == 判断是不是同类
         * ========================================================= */
        Class<?> cls = s1.getClass();
        System.out.println("类名 = " + cls.getName()); // 输出: com.betterchinglish.demo12_Object.Student
        System.out.println(s1.getClass() == s2.getClass()); // 输出: true（都是 Student 类）


        /* =========================================================
         * 5. clone() —— 复制对象
         *    作用：生成一个和当前对象内容一样的新对象（浅拷贝）
         *    入参：无   出参：Object（需强转）
         *    前提：类要实现 Cloneable 接口，否则抛异常
         *    验证：克隆体和原对象“内容相同但地址不同”
         * ========================================================= */
        Student s1Clone = s1.clone();
        System.out.println("克隆体 = " + s1Clone);          // 输出: Student{name='张三', age=18}
        System.out.println("克隆体.equals原对象 = " + s1Clone.equals(s1)); // 输出: true
        System.out.println("克隆体 == 原对象 = " + (s1Clone == s1));        // 输出: false（不是同一个对象）


        /* =========================================================
         * 6. finalize() —— 垃圾回收前的钩子（已废弃，了解即可）
         *    作用：对象被 GC 回收前 JVM 会尝试调用它（JDK 9 起已废弃）
         *    入参：无   出参：无（void）
         *    不推荐依赖它释放资源（用 try-with-resources 更可靠）
         *    这里只说明，不调用。
         * ========================================================= */
        // s1.finalize(); // 已过时，不要再用
    }
}
