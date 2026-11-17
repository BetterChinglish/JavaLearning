package com.betterchinglish.demo17_Map;

import com.betterchinglish.demo17_Map.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 模块三：HashMap 实战（存储自定义对象去重、投票与分类统计）
 * ----------------------------------------------------------------------
 * 【对应课程】：集合进阶-06、07、08
 * 1. HashMap 基本使用与自定义对象作为 Key（hashCode + equals 契约）
 * 2. 经典业务练习一：投票计数统计模型
 * 3. 经典业务练习二：班级与学生一对多关联分类模型
 */
public class D03_HashMapPracticeModule {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块三】HashMap 实战演练：自定义 Key 去重与统计模型");
        System.out.println("==================================================");

        /* =========================================================
         * 练习一：存储自定义对象作为 Key
         * 核心：必须重写 Key 类的 hashCode() 和 equals()！
         * ========================================================= */
        System.out.println("--- 1. 自定义对象 Student 作为 Key ---");
        Map<Student, String> studentHomeMap = new HashMap<>();

        Student s1 = new Student(101, "张三", 18);
        Student s2 = new Student(102, "李四", 19);
        Student s3 = new Student(101, "张三", 20); // 学号和姓名与 s1 相同，但年龄不同

        studentHomeMap.put(s1, "北京");
        studentHomeMap.put(s2, "上海");
        studentHomeMap.put(s3, "广州"); // 因为 Student 的 equals 和 hashCode 仅依据 id 和 name，所以会覆盖 s1！

        System.out.println("Student 作 Key 的 Map 大小 (预期覆盖后为 2): " + studentHomeMap.size());
        studentHomeMap.forEach((student, home) ->
                System.out.println("  " + student + " 籍贯: " + home)
        );

        /* =========================================================
         * 练习二：经典投票计数统计模型（利用 Map 统计元素频次）
         * 需求：80 个学生对 4 个景点（A, B, C, D）进行投票，统计每个景点的最终票数并找出票数最多的景点。
         * ========================================================= */
        System.out.println("\n--- 2. 经典统计模型：80 人景点投票统计 ---");
        String[] spots = {"A-故宫", "B-长城", "C-颐和园", "D-兵马俑"};
        List<String> voteList = new ArrayList<>();
        Random random = new Random(42); // 固定随机种子以便结果稳定复现
        for (int i = 0; i < 80; i++) {
            voteList.add(spots[random.nextInt(spots.length)]);
        }

        // 使用 Map 进行频次统计
        Map<String, Integer> countMap = new HashMap<>();
        for (String spot : voteList) {
            // 现代优雅写法：map.merge 或 getOrDefault
            countMap.put(spot, countMap.getOrDefault(spot, 0) + 1);
        }

        System.out.println("投票统计结果明细:");
        String maxSpot = null;
        int maxVotes = -1;
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " 得票数: " + entry.getValue());
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                maxSpot = entry.getKey();
            }
        }
        System.out.println("【最终获胜景点】: " + maxSpot + "，总得票: " + maxVotes + " 票");

        /* =========================================================
         * 练习三：一对多分层嵌套模型（省份 -> 市列表）
         * ========================================================= */
        System.out.println("\n--- 3. 嵌套复杂集合：省份与城市列表 ---");
        Map<String, List<String>> provinceMap = new HashMap<>();
        provinceMap.put("江苏省", List.of("南京", "苏州", "无锡", "常州"));
        provinceMap.put("浙江省", List.of("杭州", "宁波", "温州", "绍兴"));

        provinceMap.forEach((province, cities) ->
                System.out.println("  " + province + " 辖下城市: " + String.join(", ", cities))
        );
        System.out.println();
    }
}
