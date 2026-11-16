package com.betterchinglish.demo16_Collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 模块六：Collections 集合实用工具类精讲
 * -------------------------------------------------------------
 * 包含：排序、反转、洗牌、二分查找、极值、只读与同步安全包装。
 */
public class D06_CollectionsUtilModule {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块六】Collections 工具类常用算法与安全包装");
        System.out.println("==================================================");

        List<Integer> nums = new ArrayList<>(List.of(40, 10, 50, 20, 30));
        System.out.println("原始数据: " + nums);

        /* ---------------------------------------------------------
         * 1. 排序 (sort) 与 反转 (reverse)
         * --------------------------------------------------------- */
        Collections.sort(nums); // 自然升序
        System.out.println("1. 升序排序 (sort): " + nums);

        Collections.reverse(nums); // 反转列表
        System.out.println("   列表反转 (reverse): " + nums);

        /* ---------------------------------------------------------
         * 2. 随机打乱 / 洗牌 (shuffle)
         * --------------------------------------------------------- */
        Collections.shuffle(nums);
        System.out.println("2. 随机洗牌 (shuffle): " + nums);

        /* ---------------------------------------------------------
         * 3. 二分查找 (binarySearch)
         *    注意：使用二分查找前，列表必须事先处于升序排好序的状态！
         * --------------------------------------------------------- */
        Collections.sort(nums);
        int index = Collections.binarySearch(nums, 30);
        System.out.println("3. 重新升序后: " + nums + "，查找数字 30 的索引: " + index);

        /* ---------------------------------------------------------
         * 4. 最值查找 (max / min)
         * --------------------------------------------------------- */
        System.out.println("4. 最大值 (max): " + Collections.max(nums) + "，最小值 (min): " + Collections.min(nums));

        /* ---------------------------------------------------------
         * 5. 只读视图包装：unmodifiableList
         *    保护对外暴露的内部集合不被恶意篡改
         * --------------------------------------------------------- */
        List<Integer> readOnlyList = Collections.unmodifiableList(nums);
        try {
            readOnlyList.add(999); // 抛出异常
        } catch (UnsupportedOperationException e) {
            System.out.println("5. 只读包装保护成功：企图修改已拦截抛出 " + e.getClass().getSimpleName());
        }

        /* ---------------------------------------------------------
         * 6. 线程同步包装：synchronizedList
         *    将非线程安全的 ArrayList 包装为带互斥锁的列表（粗粒度同步）
         * --------------------------------------------------------- */
        List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());
        syncList.add(100);
        System.out.println("6. 线程安全同步包装成功: " + syncList);
        System.out.println();
    }
}
