# Java Collection 单列集合体系深度精讲

本模块为 Java Collection 体系的系统化学习与实战总结，配套模块源码均位于当前包下，以 [`Test.java`](Test.java) 为主执行入口。

---

## 1. 模块代码导航

| 模块文件 | 教学主题 | 核心关注点 |
| :--- | :--- | :--- |
| [`Test.java`](Test.java) | **主入口驱动类** | 统筹编排所有模块的按序执行与综合实验 |
| [`D01_CollectionOverview.java`](D01_CollectionOverview.java) | **顶层公共 API** | `add`, `contains`, `remove`, `toArray`, `size`, `isEmpty` |
| [`D02_ListModule.java`](D02_ListModule.java) | **List 体系精讲** | `ArrayList` 1.5倍扩容机制、`LinkedList` 双向链表、`Arrays.asList` 避坑 |
| [`D03_SetModule.java`](D03_SetModule.java) | **Set 体系精讲** | `HashSet` 哈希去重、`LinkedHashSet` 保持顺序、`TreeSet` 自动排序 |
| [`D04_QueueDequeModule.java`](D04_QueueDequeModule.java) | **队列与栈体系** | FIFO 队列、`ArrayDeque` 替代 `Stack`、`PriorityQueue` 优先级堆 |
| [`D05_IteratorAndFailFastModule.java`](D05_IteratorAndFailFastModule.java) | **遍历与 fail-fast 避坑** | 4种遍历方式、循环删除 `ConcurrentModificationException` 根源剖析与正确方案 |
| [`D06_CollectionsUtilModule.java`](D06_CollectionsUtilModule.java) | **Collections 工具类** | 排序、洗牌、二分查找、极值、`unmodifiableList` 与 `synchronizedList` |
| [`D07_CollectionOrderModule.java`](D07_CollectionOrderModule.java) | **顺序特性横向大比拼** | 存取顺序 vs 大小排序 vs 哈希无序实战验证与底层原理 |
| [`D08_GenericsModule.java`](D08_GenericsModule.java) | **泛型体系与 PECS** | 泛型类/接口/方法、PECS 通配符法则（生产者 extends / 消费者 super）、类型擦除 |
| [`D09_TreeDataStructureModule.java`](D09_TreeDataStructureModule.java) | **树形结构与红黑树** | BST 二叉查找树缺陷、AVL 树 4 种旋转（LL/RR/LR/RL）、红黑树 5 大规则与平衡修复 |
| [`model/Student.java`](model/Student.java) | **实战辅助模型类** | 演示 `Comparable` 自然排序规则与 `hashCode` + `equals` 去重契约 |

---

## 2. 集合体系核心接口全景横向对比表

| 顶层接口 | 允许重复？ | 存取顺序？ | 大小排序？ | 拥有索引？ | null 元素？ | 常用代表实现类 |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| **`List`** | **是** | **保证一致** | 否（默认） | **是**（`0 ~ size-1`） | 允许任意多个 | `ArrayList`, `LinkedList` |
| **`HashSet`** | **否** | **无序** | 否 | **无** | 允许最多 1 个 | 基于 `HashMap` |
| **`LinkedHashSet`** | **否** | **保证一致** | 否 | **无** | 允许最多 1 个 | 哈希表 + 双向链表 |
| **`TreeSet`** | **否** | **无** | **自动排好** | **无** | 不允许（触发 NPE） | 基于红黑树（`TreeMap`） |
| **`Queue`** | 是 | 先进先出 FIFO | 否 | 无 | 绝大多数不允许 | `LinkedList`, `ArrayDeque` |
| **`Deque`** | 是 | 双端出入 FIFO/LIFO | 否 | 无 | 绝大多数不允许 | `ArrayDeque`（现代栈推荐） |
| **`PriorityQueue`** | 是 | 无 | **按优先级出队** | 无 | 不允许 | 基于二叉堆（小顶堆/大顶堆） |

---

## 3. List 体系深度对比：ArrayList vs LinkedList vs Vector

| 评估维度 | `ArrayList` | `LinkedList` | `Vector`（过时） |
| :--- | :--- | :--- | :--- |
| **底层数据结构** | 动态 `Object[]` 数组 | 双向链表（Node 节点维护 `prev` / `next`） | 动态 `Object[]` 数组 |
| **初始化容量** | 无参构造为空数组 `{}`，首次 `add` 分配 **10** | 无需预分配容量，添加节点即 `new Node` | 默认初始容量 **10** |
| **扩容系数** | 容量不足时扩容为原大小的 **1.5 倍** | 无扩容概念，内存不连续，动态申请节点 | 扩容为原大小的 **2 倍** |
| **随机访问 `get(i)`** | **$O(1)$**（极快，通过连续内存地址直接偏移寻址） | **$O(n)$**（较慢，折半向中心遍历节点） | $O(1)$（但带锁，性能偏低） |
| **中间插入/删除** | **$O(n)$**（需调用 `System.arraycopy` 平移后续元素） | **$O(1)$**（在定位到节点后仅需修改指针） | $O(n)$ |
| **CPU 缓存亲和性** | **高**（内存连续分布，L1/L2 缓存行预读命中率高） | **低**（节点散落在堆各处，缓存命中率差） | 高 |
| **线程安全性** | 非线程安全 | 非线程安全 | 线程安全（每个方法加 `synchronized`） |
| **综合推荐场景** | **绝大多数业务场景的首选 List** | 仅适合头尾频繁出入（也更推荐 `ArrayDeque`） | **已被完全淘汰**，不建议在任何新代码中使用 |

---

## 4. Set 体系深度对比：HashSet vs LinkedHashSet vs TreeSet

| 评估维度 | `HashSet` | `LinkedHashSet` | `TreeSet` |
| :--- | :--- | :--- | :--- |
| **底层实现机制** | `HashMap` 的 Key 存储数据 | `LinkedHashMap`（哈希表 + 全局双向链表） | 红黑树（`TreeMap` 的 Key 存储数据） |
| **去重与判重依据** | **`hashCode()` 桶定位 + `equals()` 比对** | 同 `HashSet`（`hashCode` + `equals`） | **`compareTo()` 或 `compare()` 是否为 0** |
| **存取顺序 (Insertion)** | **无序**（由哈希值与数组长度映射决定） | **严格等于存入顺序** | 无序（被大小重新排列覆盖） |
| **大小排序 (Sorted)** | 否 | 否 | **是**（自然升序或自定义比较器） |
| **时间复杂度** | 增删查 **$O(1)$** | 增删查 **$O(1)$**（略有一点链表指针开销） | 增删查 **$O(\log n)$**（二叉平衡树检索） |
| **典型应用场景** | 纯粹去重且不在乎任何顺序的高性能场景 | 既要**去重**又要**保持输入先后顺序** | 需要集合元素**随时随地自动排好序** |

### 4.1 TreeSet 深度解密：到底调用 compare 还是 compareTo？

`TreeSet` 底层依托 `TreeMap.put` 实现，比较调用逻辑**遵循严密的优先级分支**：

```
                              TreeSet.add(e)
                                    │
                  ┌─────────────────┴─────────────────┐
     构造时传入了 Comparator？               构造时未传入 Comparator (无参构造)
                  │                                   │
                  ▼                                   ▼
        调用 comparator.compare()          强制将元素强转为 (Comparable)
                  │                                   │
   元素【不需要】实现 Comparable 接口        若元素未实现 Comparable 接口
   若元素也实现了，其 compareTo 会被完全覆盖!          ▼ 直接抛出运行期异常！
                                            ClassCastException: cannot be cast to Comparable
```

1. **没有传入 `Comparator` 时**：
   - 走**自然排序**，强制要求元素必须实现 `Comparable` 接口并重写 `compareTo(T o)`。
   - 若元素没有实现 `Comparable`，在第一次添加或比较时直接抛出 `ClassCastException`。
2. **传入了 `Comparator` 时**：
   - 走**定制排序**，强制调用 `comparator.compare(o1, o2)`。
   - 此时元素**完全不需要**实现 `Comparable` 接口，甚至第三方不可修改源码的类也能放入。
3. **两者并存时**：
   - 外部传入的 `Comparator` 具有**绝对最高优先级**，元素自身的 `compareTo` 会被彻底忽略。
4. **判重/去重唯一依据**：
   - 仅依据比较器返回值是否为 0（`cmp == 0`）。**完全不调用元素的 `equals()` 和 `hashCode()`**！若两个对象属性不同但比较器返回 0，后加入的对象会被直接吃掉（判定为重复）。

---

## 5. Queue 与 Deque 体系对比：先进先出、双端队列与堆

| 实现类 | 接口类型 | 底层数据结构 | 元素出入模式 | 关键优势与适用场景 |
| :--- | :--- | :--- | :--- | :--- |
| **`LinkedList`** | `Queue` & `Deque` | 双向链表 | FIFO 队列 / LIFO 栈 | 通用队列，无扩容数组平移，但节点对象有额外指针开销 |
| **`ArrayDeque`** | `Deque` | 循环动态数组 | 双端可进可出 | **高性能双端队列与现代“栈（Stack）”官方首选**，连续内存性能极强 |
| **`PriorityQueue`** | `Queue` | 二叉堆（平衡小顶堆/大顶堆） | 优先级最高者最先出队 | Top-K 问题、带权重的任务调度、贪心算法题核心底座 |

---

## 6. 核心专题：集合“添加顺序”横向透视

> **一句话总结**：集合有没有顺序，取决于底层存储是“数组下标”、“哈希桶位置”还是“二叉树节点”。

```
输入序列: ["Spring", "Summer", "Autumn", "Winter"]
│
├── ArrayList        ──> [Spring, Summer, Autumn, Winter] (原汁原味：严格存取顺序)
│
├── HashSet          ──> [Autumn, Winter, Summer, Spring] (哈希桶分布：无序，与输入先后无关)
│
├── LinkedHashSet    ──> [Spring, Summer, Autumn, Winter] (双向链表牵引：既去重又保存取顺序)
│
└── TreeSet          ──> [Autumn, Spring, Summer, Winter] (红黑树排序：按 A-Z 字典升序排好)
```

### 三大概念清晰辨析
1. **存取顺序（Insertion Order）**：存入第 1 个遍历出来就是第 1 个。`List` 全系、`LinkedHashSet`、`ArrayDeque` 强力保证。
2. **大小排序（Sorted Order）**：集合会自动维护从小到大或从大到小的规则。`TreeSet`、`PriorityQueue` 强力保证。
3. **无序（Unordered）**：`HashSet` 既不按存入顺序排，也不按大小排。
   - **注意**：“无序”不是指“每次打印都随机乱跳”，而是指“不承诺与存入顺序一致”；在数据量稳定不扩容时，单次 JVM 中遍历顺序是固定的。

---

## 7. 遍历机制与 fail-fast 快速失败避坑表

| 遍历方式 | 适用范围 | 是否支持遍历中删除？ | 核心底层原理与注意事项 |
| :--- | :--- | :---: | :--- |
| **普通 for 循环** | 仅限 `List`（必须有索引） | **部分支持**（容易漏删） | 容易因索引平移出现“漏删”或“下标越界”，如删除后必须 `i--` |
| **增强 for 循环** | 所有 `Collection` | **绝对不可直接调 `list.remove()`** | 底层由编译器转为 `Iterator`。直接 `remove` 会触发 `ConcurrentModificationException`！ |
| **`Iterator` 迭代器** | 所有 `Collection` | **支持**（调用 `it.remove()`） | 安全！`iterator.remove()` 在底层删除后会执行 `expectedModCount = modCount` 同步 |
| **Lambda `forEach`** | 所有 `Collection` | **不可在 Lambda 内调 `remove()`** | 仅用于函数式只读消费；需条件删除时使用 `list.removeIf(Predicate)`（推荐） |

---

## 8. 集合技术选型黄金决策树

```
                         需要存多个元素？
                               │
               ┌───────────────┴───────────────┐
             单列元素？                      键值对 Key-Value？
               │                               │
          [Collection]                       [Map]
               │
      ┌────────┴────────┬──────────────────────┐
   元素需要重复？      必须去重？              需要排队调度？
      │                 │                      │
    [List]            [Set]                 [Queue/Deque]
      │                 │                      │
 ┌────┴────┐     ┌──────┼────────┐      ┌──────┼────────┐
随机查多   头尾多  不在乎顺序  保持存入顺序 自动排好序  常规FIFO   替代Stack   优先级堆
 │         │       │          │        │       │          │         │
ArrayList  LinkedList  HashSet  LinkedHashSet  TreeSet  ArrayDeque  ArrayDeque PriorityQueue
```

---

## 9. 泛型体系与通配符 PECS 黄金法则（对应课程 08、09）

### 9.1 通配符对比速查表

| 通配符语法 | 角色定位 | 读写权限 | 核心应用场景与限制 |
| :--- | :---: | :---: | :--- |
| **`<? extends T>`** | **生产者 (Producer)** | **只读安全** | 集合作为生产者向外提供数据；**禁止写入**任何对象（除了 `null`），因为编译器无法确定实际具体子类型。 |
| **`<? super T>`** | **消费者 (Consumer)** | **写入安全** | 集合作为消费者接收数据；**允许存入 `T` 及其子类**；读取出来的元素只能强转或当作 `Object` 处理。 |
| **`<?>`** | 无界通配符 | 只读 | 适用于只使用 `Object` 基础方法、完全不关心具体类型的场景。 |

> **PECS 助记口诀**：*Producer Extends, Consumer Super*。频繁读取选 `extends`，频繁写入选 `super`。

### 9.2 泛型底层本质：类型擦除 (Type Erasure)
- Java 的泛型是**编译期语法糖（伪泛型）**。
- 编译后字节码中无边界泛型统一擦除为 `Object`，有边界泛型擦除为其上界类。
- **为什么不能 `new T()`**？因为运行期类型已被擦除，JVM 无法得知具体类元信息。
- **为什么不能创建泛型数组**？（如 `new ArrayList<String>[10]`），因为数组在运行期具有类型协变检查机制，与擦除后的类型安全产生冲突。

---

## 10. 树形数据结构底层剖析（对应课程 10、11、12）

### 10.1 BST ➔ AVL 树 ➔ 红黑树演进对比

| 树结构 | 平衡条件 | 优缺点对比 | 适用场景 |
| :--- | :--- | :--- | :--- |
| **二叉查找树 (BST)** | 左小右大 | 插入有序数据时会退化为**单向链表**，查找由 $O(\log n)$ 恶化为 $O(n)$ | 基础教学模型 |
| **平衡二叉树 (AVL)** | 任意节点左右子树高度差绝对值 $\le 1$ | 极度严格平衡，查询快，但**增删时旋转频繁**，写性能开销大 | 查多写极少的场景 |
| **红黑树 (R-B Tree)** | 遵循 5 大红黑规则（最长路径不超过最短路径的 2 倍） | **弱平衡二叉树**，通过变色与局部旋转维护平衡，查写综合性能最高 | `TreeSet`, `TreeMap`, JDK 8+ `HashMap` |

### 10.2 AVL 树 4 种旋转机制
1. **LL（左左失衡）**：左孩子的左子树插入导致 ➜ **单次右旋**。
2. **RR（右右失衡）**：右孩子的右子树插入导致 ➜ **单次左旋**。
3. **LR（左右失衡）**：左孩子的右子树插入导致 ➜ **先左孩子左旋，再根节点右旋（双旋）**。
4. **RL（右左失衡）**：右孩子的左子树插入导致 ➜ **先右孩子右旋，再根节点左旋（双旋）**。

### 10.3 红黑树 5 大红黑规则（必背核心）
1. 每个节点要么是**红色**，要么是**黑色**。
2. **根节点必须是黑色**。
3. 叶子节点（NIL 空节点）必须是黑色。
4. **不能出现连续两个红色节点**（红色节点的父与子必须为黑）。
5. 从任一节点到其所有叶子节点的路径上，**黑色节点数量必须相同（黑高一致）**。

### 10.4 新增节点平衡修复策略
新节点默认染为**红色**（避免破坏规则 5 的黑高）：
- **父节点为黑**：直接添加成功，无需调整。
- **父节点为红（红红冲突）**：
  - **叔叔节点为红**：**变色**（父与叔变黑，祖父变红，递归向上检查祖父）。
  - **叔叔节点为黑或空**：**旋转 + 变色**（依 LL/RR/LR/RL 模式单旋或双旋并重新染色）。
