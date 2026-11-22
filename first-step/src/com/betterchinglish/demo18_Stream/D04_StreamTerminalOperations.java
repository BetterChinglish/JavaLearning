package com.betterchinglish.demo18_Stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 模块四：Stream 流的终结方法详解（Terminal Operations）
 * ----------------------------------------------------------------------
 * 【对应课程】：Stream流-04-Stream流终结方法详解
 * 【核心特征】：
 *   1. 终结管道：终结操作是触发流水线真正执行的唯一开关。
 *   2. 产生结果或副作用：终结操作不再返回 Stream，而是返回具体的数值、对象、数组、集合，或仅产生遍历副作用（如 forEach）。
 *   3. 短路优化（Short-circuiting）：部分终结操作（如 anyMatch、findFirst）在满足条件后会立即中止流水线，无需处理剩余全部元素。
 *
 * 【核心 API 详解】：
 *   - forEach(Consumer<T>): 遍历流中每个元素
 *   - count(): 统计流中元素个数
 *   - toArray() / toArray(IntFunction<A[]> generator): 将流转为数组
 *   - max(Comparator<T>) / min(Comparator<T>): 获取最大值 / 最小值（返回 Optional<T>）
 *   - reduce(...): 核心规约操作，用于将流中所有元素结合起来，生成单一汇总结果（如求和、求积）
 *   - anyMatch / allMatch / noneMatch: 元素匹配判断（支持短路求值）
 *   - findFirst / findAny: 查找首个或任意匹配元素
 */
public class D04_StreamTerminalOperations {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块四】Stream 流的常用终结操作详解");
        System.out.println("==================================================");

        List<String> fruits = List.of("Apple", "Banana", "Orange", "Watermelon", "Pear");

        /* ---------------------------------------------------------
         * 1. forEach 与 count
         * --------------------------------------------------------- */
        System.out.println("--- 1. forEach 遍历与 count 计数 ---");
        System.out.print("forEach 打印: ");
        fruits.stream().forEach(f -> System.out.print(f + " "));
        System.out.println();

        long count = fruits.stream().filter(f -> f.length() > 5).count();
        System.out.println("长度大于 5 的水果个数 (count): " + count);

        /* ---------------------------------------------------------
         * 2. toArray 收集为数组（无参 vs 指定类型生成器）
         * --------------------------------------------------------- */
        System.out.println("\n--- 2. toArray 转数组（类型安全技巧） ---");
        // (A) 无参 toArray()：返回 Object[]，需要强转，不够安全
        Object[] objArr = fruits.stream().toArray();
        System.out.println("无参 toArray 返回类型: " + objArr.getClass().getSimpleName() + ", 内容: " + Arrays.toString(objArr));

        // (B) 有参 toArray(IntFunction<A[]> generator)：
        // 传递数组构造方法引用 String[]::new，等价于 len -> new String[len]
        String[] strArr = fruits.stream().toArray(String[]::new);
        System.out.println("带参 toArray(String[]::new) 返回精确类型: " + strArr.getClass().getSimpleName() + ", 内容: " + Arrays.toString(strArr));

        /* ---------------------------------------------------------
         * 3. max 与 min（结合 Optional 安全防护）
         * --------------------------------------------------------- */
        System.out.println("\n--- 3. max 与 min 最值查询 ---");
        List<Integer> scores = List.of(88, 92, 65, 77, 99, 58);

        // 获取最高分与最低分
        Optional<Integer> maxOpt = scores.stream().max(Integer::compareTo);
        Optional<Integer> minOpt = scores.stream().min(Integer::compareTo);

        // Optional 提供了 orElse / ifPresent，彻底告别空指针
        System.out.println("最高分 (max): " + maxOpt.orElse(-1));
        System.out.println("最低分 (min): " + minOpt.orElse(-1));

        /* ---------------------------------------------------------
         * 4. reduce 规约操作（求和、求积、字符拼接）
         * --------------------------------------------------------- */
        System.out.println("\n--- 4. reduce 核心规约（累加器） ---");
        List<Integer> nums = List.of(1, 2, 3, 4, 5);

        // reduce(identity, BinaryOperator): 第一个参数为初始默认值，第二个参数为累加函数
        // 计算过程：(((1 + 2) + 3) + 4) + 5 = 15
        int sum = nums.stream().reduce(0, (acc, item) -> acc + item);
        System.out.println("1 到 5 累加求和: " + sum);

        // 方法引用简写为 Integer::sum
        int sumWithRef = nums.stream().reduce(0, Integer::sum);
        System.out.println("使用方法引用求和: " + sumWithRef);

        // 累乘求积
        int product = nums.stream().reduce(1, (acc, item) -> acc * item);
        System.out.println("1 到 5 累乘求积: " + product);

        /* ---------------------------------------------------------
         * 5. anyMatch / allMatch / noneMatch 匹配判断与短路机制
         * --------------------------------------------------------- */
        System.out.println("\n--- 5. 匹配判断（Match）与短路执行（Short-circuiting） ---");
        List<Integer> ageList = List.of(16, 18, 22, 25, 30);

        // anyMatch：只要有一个满足条件即可
        boolean hasAdult = ageList.stream().anyMatch(age -> age >= 18);
        System.out.println("是否存在成年人 (anyMatch >= 18): " + hasAdult);

        // allMatch：必须全部满足条件
        boolean allAdult = ageList.stream().allMatch(age -> age >= 18);
        System.out.println("是否全员成年 (allMatch >= 18): " + allAdult);

        // noneMatch：必须都不满足条件
        boolean noNegative = ageList.stream().noneMatch(age -> age < 0);
        System.out.println("是否没有任何负数年龄 (noneMatch < 0): " + noNegative);

        // 短路实验：当判断 anyMatch 时，遇到第一个 true 会直接中断后续遍历
        System.out.print("anyMatch 短路验证流程: ");
        boolean matchShortCircuit = ageList.stream()
                .peek(age -> System.out.print(age + " "))
                .anyMatch(age -> age >= 18);
        System.out.println("\n【结论】：在遇到 18 判定为 true 后，22, 25, 30 均未被遍历，这就是 Stream 的短路优化！\n");
    }
}
