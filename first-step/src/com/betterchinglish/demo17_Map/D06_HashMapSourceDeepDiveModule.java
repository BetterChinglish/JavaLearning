package com.betterchinglish.demo17_Map;

/**
 * 模块六：HashMap 核心源码深度逐行剖析（一 ~ 四）
 * ----------------------------------------------------------------------
 * 【对应课程】：集合进阶-13、14、15、16
 * 【核心内容】：
 * 1. 核心静态常量与内存成员变量
 * 2. 构造方法与延迟初始化设计
 * 3. putVal 插入核心流程与树化/退化全逻辑
 * 4. resize 扩容机制与巧妙的高低位指针链表拆分
 */
public class D06_HashMapSourceDeepDiveModule {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块六】HashMap 核心源码超详细逐行深度剖析");
        System.out.println("==================================================");

        /* =========================================================
         * 源码篇（一）：核心常量与成员变量
         * ========================================================= */
        System.out.println("--- 源码篇（一）：核心常量与内存模型 ---");
        System.out.println("1. DEFAULT_INITIAL_CAPACITY = 1 << 4 (16)：默认初始容量为 16（必须是 2 的幂次方）。");
        System.out.println("2. MAXIMUM_CAPACITY = 1 << 30：最大容量 2^30。");
        System.out.println("3. DEFAULT_LOAD_FACTOR = 0.75f：默认负载因子。");
        System.out.println("   - 为什么是 0.75？泊松分布（Poisson Distribution）空间与时间的最佳折中点。");
        System.out.println("4. TREEIFY_THRESHOLD = 8：树化阈值。单条链表长度达到 8 时尝试树化。");
        System.out.println("5. UNTREEIFY_THRESHOLD = 6：退化阈值。红黑树节点因扩容或删除减少到 6 时退化为单向链表。");
        System.out.println("   - 为什么中间留出 7 作为缓冲？防止节点在 7-8 之间频繁树化与退化，消耗 CPU 性能！");
        System.out.println("6. MIN_TREEIFY_CAPACITY = 64：最小树化数组容量。链表达到 8 但总容量不足 64 时，优先扩容而不树化！");
        System.out.println("7. table：transient Node<K,V>[] table 哈希桶底层数组。");
        System.out.println("8. modCount：记录集合结构性变动次数（fail-fast 机制）。");

        /* =========================================================
         * 源码篇（二）：构造函数与扰动函数设计
         * ========================================================= */
        System.out.println("\n--- 源码篇（二）：构造函数与扰动函数 ---");
        System.out.println("1. 构造函数 new HashMap<>()：");
        System.out.println("   - 仅执行 this.loadFactor = DEFAULT_LOAD_FACTOR; 并没有在堆中创建 table 数组！");
        System.out.println("   - 采用【延迟初始化 (Lazy Initialization)】设计，第一次 put 时才真正分配内存数组。");
        System.out.println("2. 高位扰动函数 hash(key)：");
        System.out.println("   static final int hash(Object key) {");
        System.out.println("       int h;");
        System.out.println("       return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);");
        System.out.println("   }");
        System.out.println("   - 作用：将 32 位 hashCode 的高 16 位与低 16 位进行异或。");
        System.out.println("   - 原理：数组长度通常较小，直接做 (n - 1) & hash 只有低位起作用；通过高位扰动让高位特征也参与运算，大幅减少碰撞！");

        /* =========================================================
         * 源码篇（三）：put / putVal 核心写入执行链
         * ========================================================= */
        System.out.println("\n--- 源码篇（三）：putVal 核心写入流程图解 ---");
        System.out.println("Step 1: 检查 table 是否为空或长度为 0，若为空调用 resize() 进行首次初始化分配（容量 16）。");
        System.out.println("Step 2: 根据 i = (n - 1) & hash 计算数组下标。");
        System.out.println("        - 若 table[i] == null，说明无冲突，直接 new Node 放入桶中。");
        System.out.println("Step 3: 若 table[i] 存在冲突：");
        System.out.println("        a. 判断首节点的 hash、key 是否完全一致（key == k || key.equals(k)），若一致则直接覆盖。");
        System.out.println("        b. 若首节点是 TreeNode（红黑树），调用 putTreeVal 进行红黑树插入。");
        System.out.println("        c. 若首节点是普通链表 Node：遍历链表（尾插法）：");
        System.out.println("           - 途中发现相同的 key，跳出遍历并覆盖 value。");
        System.out.println("           - 遍历到底部未找到，将新节点挂在末尾。");
        System.out.println("           - 检查链表长度是否 >= 8：若达到，调用 treeifyBin(tab, hash) 准备树化！");
        System.out.println("Step 4: 元素个数 ++size，判断是否超过 threshold（capacity * 0.75），超过则触发 resize() 扩容。");

        /* =========================================================
         * 源码篇（四）：resize() 扩容算法与巧妙的高低位迁移
         * ========================================================= */
        System.out.println("\n--- 源码篇（四）：resize 扩容与高低位链表拆分 ---");
        System.out.println("1. 扩容倍数：newCap = oldCap << 1（容量翻倍为原来的 2 倍）。");
        System.out.println("2. 重新散列优化（JDK 8 巧妙设计）：");
        System.out.println("   - JDK 7 中扩容需要对所有元素重新调用 hash() 重新计算下标，非常耗时且容易成环。");
        System.out.println("   - JDK 8 中：因为容量是 2 的幂次方，扩容后高位要么是 0，要么是 1！");
        System.out.println("   - 只需要看 (e.hash & oldCap) == 0 ：");
        System.out.println("     * 等于 0：节点在新数组中的下标【保持不变】（低位链 loHead -> loTail）。");
        System.out.println("     * 等于 1：节点在新数组中的下标 =【原下标 + oldCap】（高位链 hiHead -> hiTail）。");
        System.out.println("   - 拆分成两组链表后一次性挂载，免去重新哈希，且尾插法保持原有顺序，避免死循环！");
        System.out.println();
    }
}
