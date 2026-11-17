package com.betterchinglish.demo17_Map;

/**
 * Java Map 双列集合体系教学总入口
 * -------------------------------------------------------------
 * 【学习路径】
 * 1. D01_MapOverviewAndApi              : 双列集合特点与 Map 顶层核心常用 API
 * 2. D02_MapTraverseModule              : Map 三种经典遍历方式（keySet、entrySet、forEach）
 * 3. D03_HashMapPracticeModule          : HashMap 实战（自定义对象去重、投票计数、分层嵌套）
 * 4. D04_LinkedHashMapModule            : LinkedHashMap 存取顺序与 LRU 缓存实战
 * 5. D05_TreeMapPracticeModule          : TreeMap 排序实战（自然排序、自定义比较器、字符频次统计）
 * 6. D06_HashMapSourceDeepDiveModule    : HashMap 核心源码逐行深度剖析（常量、扰动函数、putVal、resize高低位拆分）
 * 7. D07_TreeMapSourceAndCollectionsModule : TreeMap 源码剖析、可变参数本质与 Collections 工具类
 *
 * 可以直接运行 main 方法进行全流程学习，也可以单独针对某个模块进行断点调试与实验。
 */
public class Test {

    public static void main(String[] args) {

        System.out.println("############################################################");
        System.out.println("###         欢迎进入 Java Map 双列集合体系完整教学实验       ###");
        System.out.println("############################################################\n");

        // 模块一：双列集合特点与常用 API（put 返回值、getOrDefault、三种视图）
        D01_MapOverviewAndApi.learn();

        // 模块二：Map 三种遍历方式（keySet 键找值、entrySet 键值对、Lambda forEach）
        D02_MapTraverseModule.learn();

        // 模块三：HashMap 经典实战（自定义对象作为 Key、投票统计模型、一对多嵌套）
        D03_HashMapPracticeModule.learn();

        // 模块四：LinkedHashMap 存取顺序特性与手写 LRU 缓存
        D04_LinkedHashMapModule.learn();

        // 模块五：TreeMap 自然排序、外部 Comparator 与字符字典序统计实战
        D05_TreeMapPracticeModule.learn();

        // 模块六：HashMap 核心源码超详细逐行深度剖析（常量、扰动、putVal流程、resize高低位拆分）
        D06_HashMapSourceDeepDiveModule.learn();

        // 模块七：TreeMap 源码精析、可变参数底层本质与 Collections 工具类全套操作
        D07_TreeMapSourceAndCollectionsModule.learn();

        System.out.println("############################################################");
        System.out.println("###          恭喜！Java Map 双列集合体系全部模块演练完成       ###");
        System.out.println("############################################################");
    }
}
