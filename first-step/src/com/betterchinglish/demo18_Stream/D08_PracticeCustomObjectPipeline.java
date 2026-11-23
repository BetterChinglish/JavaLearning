package com.betterchinglish.demo18_Stream;

import com.betterchinglish.demo18_Stream.model.Actor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 模块八：综合练习 3 - 自定义对象过滤、合并、封装与多维收集
 * ----------------------------------------------------------------------
 * 【对应课程】：Stream流-08-综合练习3-自定义对象过滤并收集
 * 【经典实战模型（演艺圈演员流水线选拔）】：
 *   现有两支队伍：男演员队伍 与 女演员队伍。
 *   男演员清单：["蔡徐坤,24", "叶修,23", "刘德华,40", "吴京,35", "李连杰,38", "成龙,42"]
 *   女演员清单：["杨颖,24", "杨幂,30", "赵丽颖,27", "杨紫,26", "迪丽热巴,25", "高圆圆,28"]
 *
 * 【需求流程】：
 *   1. 男演员只要名字为 3 个字的前 2 个人；
 *   2. 女演员只要姓“杨”的，并且不要第 1 个人（跳过第 1 个）；
 *   3. 将过滤后的男演员流和女演员流合并为一个流；
 *   4. 将合并后的流中每个字符串封装成自定义实体对象 Actor（包含姓名、年龄、性别）；
 *   5. 将结果按年龄降序收集为 List<Actor> 并打印；
 *   6. 进阶统计：按性别统计选拔演员的平均年龄与总数。
 */
public class D08_PracticeCustomObjectPipeline {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块八】综合练习 3：自定义对象过滤、合并与多维收集");
        System.out.println("==================================================");

        List<String> manList = List.of(
                "蔡徐坤,24", "叶修,23", "刘德华,40", "吴京,35", "李连杰,38", "成龙,42"
        );

        List<String> womanList = List.of(
                "杨颖,24", "杨幂,30", "赵丽颖,27", "杨紫,26", "迪丽热巴,25", "高圆圆,28"
        );

        System.out.println("原始男演员名单: " + manList);
        System.out.println("原始女演员名单: " + womanList);

        /* ---------------------------------------------------------
         * 步骤 1：处理男演员流
         * 需求：名字为 3 个字的前 2 个人
         * --------------------------------------------------------- */
        Stream<Actor> manStream = manList.stream()
                .filter(s -> s.split(",")[0].length() == 3) // 姓名长度 == 3
                .limit(2)                                   // 截取前 2 个
                .map(s -> {
                    String[] arr = s.split(",");
                    return new Actor(arr[0], Integer.parseInt(arr[1]), "男");
                });

        /* ---------------------------------------------------------
         * 步骤 2：处理女演员流
         * 需求：只要姓“杨”，且跳过第 1 个人
         * --------------------------------------------------------- */
        Stream<Actor> womanStream = womanList.stream()
                .filter(s -> s.split(",")[0].startsWith("杨")) // 姓杨
                .skip(1)                                      // 跳过第一个
                .map(s -> {
                    String[] arr = s.split(",");
                    return new Actor(arr[0], Integer.parseInt(arr[1]), "女");
                });

        /* ---------------------------------------------------------
         * 步骤 3 & 4 & 5：Stream.concat 合并并按年龄降序收集
         * --------------------------------------------------------- */
        System.out.println("\n--- 步骤 3 ~ 5：双流合并 -> 封装 Actor -> 按年龄降序收集 ---");
        List<Actor> finalCast = Stream.concat(manStream, womanStream)
                .sorted((a1, a2) -> a2.getAge() - a1.getAge()) // 按年龄降序
                .collect(Collectors.toList());

        System.out.println("最终入选演员阵容 (降序排列):");
        finalCast.forEach(actor -> System.out.println("  " + actor));

        /* ---------------------------------------------------------
         * 步骤 6：进阶多维统计（按性别统计人数与平均年龄）
         * --------------------------------------------------------- */
        System.out.println("\n--- 步骤 6：按性别聚合统计平均年龄 ---");
        Map<String, Double> avgAgeByGender = finalCast.stream().collect(
                Collectors.groupingBy(
                        Actor::getGender,
                        Collectors.averagingDouble(Actor::getAge)
                )
        );

        avgAgeByGender.forEach((gender, avgAge) ->
                System.out.printf("  [%s演员] 平均年龄: %.1f 岁%n", gender, avgAge));
        System.out.println();
    }
}
