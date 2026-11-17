package com.betterchinglish.demo16_Collection;

import java.util.ArrayList;
import java.util.List;

/**
 * 模块八：泛型进阶原理（类、接口、方法、通配符 PECS 原则与类型擦除）
 * ----------------------------------------------------------------------
 * 【对应课程】：集合进阶-08、09
 * 【核心价值】：泛型是 Java 集合框架的安全基石，编译期提供强类型检查，消除强制类型转换。
 */
public class D08_GenericsModule {

    /* =========================================================
     * 1. 泛型类 (Generic Class)
     * ========================================================= */
    static class Box<T> {
        private T content;

        public void put(T content) {
            this.content = content;
        }

        public T get() {
            return content;
        }

        @Override
        public String toString() {
            return "Box{" + content + "}";
        }
    }

    /* =========================================================
     * 2. 泛型接口 (Generic Interface) 与实现类
     * ========================================================= */
    interface Generator<E> {
        E next();
    }

    // 实现方式 A：实现类不保留泛型，直接指定具体类型
    static class StringGenerator implements Generator<String> {
        @Override
        public String next() {
            return "Generated-String";
        }
    }

    // 实现方式 B：实现类延续泛型参数
    static class ContainerGenerator<E> implements Generator<E> {
        private final E item;
        public ContainerGenerator(E item) { this.item = item; }
        @Override
        public E next() { return item; }
    }

    /* =========================================================
     * 3. 泛型方法 (Generic Method)
     *    注意：方法上的 <M> 与类上的泛型独立，只有修饰符后面加了 <M> 的才是泛型方法！
     * ========================================================= */
    public static <M> void printArray(M[] array) {
        System.out.print("  [泛型方法 printArray]: ");
        for (M element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    /* =========================================================
     * 辅助继承体系，用于演示通配符与 PECS 原则
     * Animal (父) <- Dog (子) <- Teddy (孙)
     * ========================================================= */
    static class Animal {
        String name;
        Animal(String name) { this.name = name; }
        @Override public String toString() { return name; }
    }
    static class Dog extends Animal {
        Dog(String name) { super(name); }
    }
    static class Teddy extends Dog {
        Teddy(String name) { super(name); }
    }

    /* =========================================================
     * 4. 通配符与 PECS 原则深度精析
     *    PECS = Producer Extends, Consumer Super
     * ========================================================= */
    /**
     * 【Producer Extends (生产者用 extends)】：
     * 如果你只需要从集合中“读取”数据（集合作为生产者生产数据），用 <? extends T>。
     * 作用：只读安全，上界为 T。
     * 限制：不能向里面 add 任何元素（除了 null），因为编译器无法确定实际的具体子类型！
     */
    private static void printAnimals(List<? extends Animal> animals) {
        System.out.print("  [Producer Extends - 读取]: ");
        for (Animal a : animals) {
            System.out.print(a + " "); // 安全读取为 Animal
        }
        System.out.println();
        // animals.add(new Dog("旺财")); // 编译报错！无法添加元素
    }

    /**
     * 【Consumer Super (消费者用 super)】：
     * 如果你需要向集合中“存入/消费”数据（集合作为消费者接收数据），用 <? super T>。
     * 作用：写入安全，下界为 T。
     * 限制：存入 T 或 T 的子类是安全的；但读取出来只能是 Object。
     */
    private static void addDogs(List<? super Dog> dogConsumer) {
        dogConsumer.add(new Dog("金毛"));   // 安全存入 Dog
        dogConsumer.add(new Teddy("小泰迪")); // 安全存入 Dog 的子类 Teddy
        System.out.println("  [Consumer Super - 写入后]: " + dogConsumer);
        // Dog d = dogConsumer.get(0); // 编译报错！读取出来只能是 Object
        Object obj = dogConsumer.get(0);
    }

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块八】泛型体系进阶：类/接口/方法、PECS 原则与类型擦除");
        System.out.println("==================================================");

        /* ---------------------------------------------------------
         * 1. 泛型类与泛型接口
         * --------------------------------------------------------- */
        System.out.println("--- 1. 泛型类与泛型接口 ---");
        Box<String> strBox = new Box<>();
        strBox.put("Java 泛型");
        System.out.println("泛型类: " + strBox.get());

        Generator<String> genA = new StringGenerator();
        System.out.println("指定具体类型实现泛型接口: " + genA.next());

        Generator<Integer> genB = new ContainerGenerator<>(1024);
        System.out.println("延续泛型参数实现泛型接口: " + genB.next());

        /* ---------------------------------------------------------
         * 2. 泛型方法
         * --------------------------------------------------------- */
        System.out.println("\n--- 2. 泛型方法 ---");
        Integer[] intArr = {1, 2, 3};
        String[] strArr = {"A", "B", "C"};
        printArray(intArr);
        printArray(strArr);

        /* ---------------------------------------------------------
         * 3. 通配符与 PECS 黄金法则实战
         * --------------------------------------------------------- */
        System.out.println("\n--- 3. 通配符与 PECS 黄金法则 (Producer Extends, Consumer Super) ---");
        List<Dog> dogList = new ArrayList<>(List.of(new Dog("柯基"), new Dog("柴犬")));
        List<Teddy> teddyList = new ArrayList<>(List.of(new Teddy("棕泰迪"), new Teddy("白泰迪")));
        List<Animal> animalList = new ArrayList<>();

        // 3.1 <? extends Animal> 适合读取（狗和泰迪都是动物，可以统一当做动物读出来）
        printAnimals(dogList);
        printAnimals(teddyList);

        // 3.2 <? super Dog> 适合存入（Animal 列表可以作为 Dog 及其子类的安全消费者）
        addDogs(animalList);

        /* ---------------------------------------------------------
         * 4. 泛型本质：类型擦除 (Type Erasure) 深度剖析
         * --------------------------------------------------------- */
        System.out.println("\n--- 4. 泛型核心底层原理：类型擦除 (Type Erasure) ---");
        List<String> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        System.out.println("List<String> 的 Class:  " + l1.getClass());
        System.out.println("List<Integer> 的 Class: " + l2.getClass());
        System.out.println("两者运行时 Class 是否完全相同: " + (l1.getClass() == l2.getClass()));

        System.out.println("\n【泛型擦除总结】");
        System.out.println("  1. Java 泛型是“伪泛型”：泛型信息仅存在于【编译期】做语法检查与安全约束。");
        System.out.println("  2. 进入字节码后，所有没有限制的泛型均被擦除为 Object；有边界的擦除为其边界类。");
        System.out.println("  3. 为什么不能 new T()？因为运行期 T 已被擦除为 Object，JVM 不知道该构造哪个具体类型。");
        System.out.println("  4. 为什么不能创建泛型数组（如 new ArrayList<String>[10]）？因为类型擦除会导致数组失去运行期类型安全的协变保护。");
        System.out.println();
    }
}
