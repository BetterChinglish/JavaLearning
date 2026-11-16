package com.betterchinglish.demo16_Collection;

/**
 * Java Collection 体系教学总入口
 * -------------------------------------------------------------
 * 【学习路径】
 * 1. D01_CollectionOverview        : Collection 顶层接口公共方法
 * 2. D02_ListModule                : List 体系（ArrayList 扩容原理与 LinkedList 双向链表）
 * 3. D03_SetModule                 : Set 体系（HashSet 去重机制与 TreeSet 排序）
 * 4. D04_QueueDequeModule          : Queue 与 Deque 体系（队列、ArrayDeque 栈与堆）
 * 5. D05_IteratorAndFailFastModule : 遍历机制与 fail-fast 快速失败深度避坑
 * 6. D06_CollectionsUtilModule     : Collections 工具类核心方法与安全包装
 * 7. D07_CollectionOrderModule     : Collection 体系“顺序特性（Order）”横向深度对比与底层原理剖析
 *
 * 可以直接运行 main 方法进行全流程学习，也可以单独注释/开启某个模块针对性调试。
 */
public class Test {

    public static void main(String[] args) {

        System.out.println("############################################################");
        System.out.println("###       欢迎进入 Java Collection 体系完整教学实验       ###");
        System.out.println("############################################################\n");

        // 模块一：Collection 顶层公共 API
        D01_CollectionOverview.learn();

        // 模块二：List 体系与底层扩容机制
        D02_ListModule.learn();

        // 模块三：Set 体系与去重/排序
        D03_SetModule.learn();

        // 模块四：Queue 与 Deque 队列/栈体系
        D04_QueueDequeModule.learn();

        // 模块五：遍历方式与快速失败（fail-fast）避坑
        D05_IteratorAndFailFastModule.learn();

        // 模块六：Collections 工具类
        D06_CollectionsUtilModule.learn();

        // 模块七：Collection 体系顺序特性（存取顺序 vs 大小排序 vs 无序）横向大比拼
        D07_CollectionOrderModule.learn();

        System.out.println("############################################################");
        System.out.println("###        恭喜！Java Collection 体系全部模块演练完成       ###");
        System.out.println("############################################################");
    }
}
