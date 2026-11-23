package com.betterchinglish.demo18_Stream;

/**
 * Java Stream 流式编程体系教学总入口
 * -------------------------------------------------------------
 * 【学习路径】
 * 1. D01_StreamQuickStart               : 初爽 Stream 流（传统循环遍历过滤 vs 现代链式流式编程）
 * 2. D02_StreamCreationAndIdea          : Stream 流的核心思想与多种获取方式（单列/双列/数组/基本类型大坑）
 * 3. D03_StreamIntermediateOperations   : Stream 流的核心中间操作（filter, limit, skip, distinct, map, flatMap, sorted, peek 与惰性求值）
 * 4. D04_StreamTerminalOperations       : Stream 流的常用终结操作（forEach, count, toArray, max/min, reduce, match 短路优化）
 * 5. D05_StreamCollectOperations        : 收集方法 collect 超详解（toList, toSet, toMap防冲突, groupingBy, partitioningBy, joining）
 * 6. D06_PracticeNumberFilter           : 综合练习 1 - 数字过滤、排序、平方和与 IntSummaryStatistics 统计全家桶
 * 7. D07_PracticeStringFilterAndCollect : 综合练习 2 - 字符串日志清洗、条件过滤与 Map 收集
 * 8. D08_PracticeCustomObjectPipeline   : 综合练习 3 - 自定义对象 Actor 过滤、双流合并、多维统计与分组实战
 *
 * 可以直接运行 main 方法进行全流程学习，也可以单独针对某个模块进行断点调试与实验。
 */
public class Test {

    public static void main(String[] args) {

        System.out.println("############################################################");
        System.out.println("###         欢迎进入 Java Stream 流式编程体系完整教学实验       ###");
        System.out.println("############################################################\n");

        // 模块一：初爽 Stream 流（体验流式编程对比传统循环的颠覆性优势）
        D01_StreamQuickStart.learn();

        // 模块二：Stream 流的思想与多种获取方式（避开基本类型数组的经典深坑）
        D02_StreamCreationAndIdea.learn();

        // 模块三：Stream 流的核心中间操作与惰性求值本质证明
        D03_StreamIntermediateOperations.learn();

        // 模块四：Stream 流的终结操作、归约计算与短路求值优化
        D04_StreamTerminalOperations.learn();

        // 模块五：收集方法 collect 超详解（Collectors 核心大全与分组分区）
        D05_StreamCollectOperations.learn();

        // 模块六：综合练习 1 - 数字过滤、规约与统计摘要
        D06_PracticeNumberFilter.learn();

        // 模块七：综合练习 2 - 字符串脏数据清洗与 Map 收集
        D07_PracticeStringFilterAndCollect.learn();

        // 模块八：综合练习 3 - 自定义对象 Actor 选拔流水线与多维统计
        D08_PracticeCustomObjectPipeline.learn();

        System.out.println("############################################################");
        System.out.println("###       恭喜！Java Stream 流式编程体系全部模块演练完成        ###");
        System.out.println("############################################################");
    }
}
