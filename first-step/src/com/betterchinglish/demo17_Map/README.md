# Java Map 双列集合体系深度精讲

本模块为 Java Map 双列键值对体系的系统化学习与实战总结，配套模块源码均位于当前包下，以 [`Test.java`](Test.java) 为主执行入口。

---

## 1. 模块代码导航

| 模块文件 | 对应课程 | 核心教学内容 |
| :--- | :--- | :--- |
| [`Test.java`](Test.java) | 全课程 | **主入口驱动类**：编排执行 D01 ~ D07 所有双列集合演练 |
| [`D01_MapOverviewAndApi.java`](D01_MapOverviewAndApi.java) | 01、02 | 双列集合特点、`put` 覆盖与旧值返回、`getOrDefault`、`keySet/values` 视图 |
| [`D02_MapTraverseModule.java`](D02_MapTraverseModule.java) | 03、04、05 | Map 三种经典遍历：`keySet`（键找值）、`entrySet`（键值对）、Lambda `forEach` |
| [`D03_HashMapPracticeModule.java`](D03_HashMapPracticeModule.java) | 06、07、08 | 自定义对象作 Key（`hashCode+equals`）、80 人投票频次统计、省市一对多嵌套 |
| [`D04_LinkedHashMapModule.java`](D04_LinkedHashMapModule.java) | 09 | `LinkedHashMap` 存取顺序保证、`accessOrder` 访问顺序模式、手写 **LRU 缓存** |
| [`D05_TreeMapPracticeModule.java`](D05_TreeMapPracticeModule.java) | 10、11、12 | `TreeMap` 自然排序、外部 `Comparator`、字符串字符频次统计（自动字典序） |
| [`D06_HashMapSourceDeepDiveModule.java`](D06_HashMapSourceDeepDiveModule.java) | 13 ~ 16 | **HashMap 核心源码四大篇章**：常量、扰动函数、`putVal` 链、`resize` 高低位拆分 |
| [`D07_TreeMapSourceAndCollectionsModule.java`](D07_TreeMapSourceAndCollectionsModule.java) | 17 ~ 20 | `TreeMap` 源码与 `fixAfterInsertion`、可变参数底层本质、`Collections` 工具类 |
| [`model/Student.java`](model/Student.java) | 辅助模型 | 演示作为 HashMap Key 时的哈希契约与作为 TreeMap Key 时的自然排序 |

---

## 2. Map 体系核心实现类横向对比矩阵

| 评估维度 | `HashMap` | `LinkedHashMap` | `TreeMap` | `Hashtable`（过时淘汰） |
| :--- | :--- | :--- | :--- | :--- |
| **底层数据结构** | 数组 + 单向链表 + 红黑树 (JDK 8+) | 哈希表 + 全局双向链表 | 红黑树（自平衡二叉查找树） | 数组 + 单向链表 |
| **键 Key 顺序特性** | **无序**（由哈希桶决定） | **严格保证存取顺序**（或访问顺序） | **自动按 Key 大小排好序** | 无序 |
| **Key 唯一性依据** | `hashCode()` 桶定位 + `equals()` 比对 | 同 `HashMap` | `compareTo()` 或 `compare()` 是否为 0 | `hashCode()` + `equals()` |
| **null 键 / null 值** | **允许 1 个 null 键**，允许多个 null 值 | 同 `HashMap` | **不允许 null 键**（抛 NPE），允许 null 值 | **不允许任何 null 键和 null 值** |
| **平均时间复杂度** | 增删查 **$O(1)$** | 增删查 **$O(1)$**（略有链表指针维护开销） | 增删查 **$O(\log n)$** | 增删查 $O(1)$（但带锁） |
| **线程安全性** | 非线程安全 | 非线程安全 | 非线程安全 | 线程安全（每个方法加 `synchronized`） |
| **适用黄金场景** | **绝大多数键值映射业务的首选** | 既要键值映射，又要**保持存入先后顺序**或实现 **LRU 缓存** | 键需要**随时随地自动排好序**或做范围查询 | 已淘汰，高并发场景改用 `ConcurrentHashMap` |

---

## 3. Map 三种遍历方式深度对比

| 遍历方式 | 核心 API / 语法 | 性能评价 | 适用场景与优缺点 |
| :--- | :--- | :---: | :--- |
| **方式一：键找值** | `map.keySet()` 循环获取 key，再 `map.get(key)` | **较慢** | **不推荐**。每次 `get` 都需要重新对 key 做一次哈希运算并在桶中查表，遍历产生二次哈希开销。 |
| **方式二：键值对 Entry** | `map.entrySet()` 循环拿到 `Map.Entry<K, V>` | **极快** | **传统代码最推荐**。Entry 对象内部同时封装了 Key 和 Value，一次寻址直接读取，无冗余查找开销。支持迭代器安全 `remove()`。 |
| **方式三：Lambda forEach** | `map.forEach((k, v) -> ...)` | **极快** | **现代代码最优雅**。底层编译器直接转为对 `entrySet()` 的遍历，代码简洁直观。 |

---

## 4. HashMap 源码四大核心机制透视（背诵级重点）

### 4.1 核心静态常量与设计意图

| 核心常量 | 源码取值 | 深度设计意图 |
| :--- | :---: | :--- |
| `DEFAULT_INITIAL_CAPACITY` | `1 << 4` (16) | 默认初始容量。必须为 2 的幂次方，便于位运算寻址替代取模。 |
| `DEFAULT_LOAD_FACTOR` | `0.75f` | 默认负载因子。基于泊松分布理论推导，在空间利用率与冲突概率之间的最佳折中。 |
| `TREEIFY_THRESHOLD` | `8` | 树化阈值。在负载因子 0.75 下，同一个桶内发生 8 次哈希碰撞的概率小于千万分之一；达到 8 说明遭遇严重哈希偏斜，必须树化为红黑树保证 $O(\log n)$ 检索。 |
| `UNTREEIFY_THRESHOLD` | `6` | 退化阈值。节点数降到 6 时退化回链表。**中间留出 7 作为缓冲带**，防止频繁在 7-8 之间增删造成剧烈树化/退化振荡。 |
| `MIN_TREEIFY_CAPACITY` | `64` | 最小树化容量。链表达到 8 但总容量不足 64 时，优先调用 `resize()` 扩容数组，只有总容量 $\ge 64$ 才真正树化。 |

### 4.2 高位扰动函数与桶寻址公式
1. **高位扰动**：`(h = key.hashCode()) ^ (h >>> 16)`
   - 作用：将 32 位整型哈希码的高 16 位右移并与低 16 位进行异或。
   - 原理：数组长度较小时（如 16），直接 `(16 - 1) & hash` 只有最低 4 位参与运算，高位特征全部丢失。高位下移异或后，混合了高低位特征，大幅降低冲突碰撞。
2. **桶寻址优化**：`index = (table.length - 1) & hash`
   - 代替 `% table.length` 取模，因为二进制位与运算在硬件指令集上效率极高。

### 4.3 JDK 8 扩容机制的“高低位拆分”奇迹
- 扩容时数组容量翻倍（`newCap = oldCap << 1`）。
- JDK 8 彻底废弃了 JDK 7 的“头插法全量重新哈希”（彻底消除了并发扩容成环死锁）。
- **判定法则**：只需计算 `(e.hash & oldCap) == 0`：
  - **等于 0（低位链）**：在新数组中的下标 **保持不变**。
  - **等于 1（高位链）**：在新数组中的下标为 **`原下标 + oldCap`**。
- 将原单向链表拆分为 `loHead` 与 `hiHead` 两组，一次性挂载，且尾插法保持原有顺序。

---

## 5. TreeMap 比较机制与字符统计模型

```
                             TreeMap.put(key, value)
                                       │
                     ┌─────────────────┴─────────────────┐
        构造时传入了 Comparator？                  未传入 Comparator (无参构造)
                     │                                   │
                     ▼                                   ▼
           走 comparator.compare()             强制强转 (Comparable<? super K>) key
                     │                                   │
      Key 【不需要】实现 Comparable 接口              若 Key 未实现 Comparable
      若 Key 自身也实现了，外部规则拥有最高优先级!               ▼ 运行期立即抛出异常！
                                               ClassCastException
```

- **字符频次统计经典范式**：
  ```java
  Map<Character, Integer> charMap = new TreeMap<>();
  for (char c : text.toCharArray()) {
      charMap.put(c, charMap.getOrDefault(c, 0) + 1);
  }
  ```

---

## 6. 可变参数（Varargs）底层本质速记
1. **语法**：`修饰符 返回值 方法名(类型... 参数名)`
2. **本质**：Java 编译器的语法糖，底层直接编译为一个**数组**（`T[]`）。
3. **两大铁律**：
   - 一个方法参数列表中**最多只能包含一个**可变参数。
   - 可变参数必须放置在形参列表的**最后一个位置**。
