package com.betterchinglish.demo16_Collection;

import java.util.ArrayList;
import java.util.List;

/**
 * 模块九：树形数据结构底层剖析（二叉查找树 BST、平衡二叉树 AVL 与红黑树 R-B Tree）
 * ----------------------------------------------------------------------
 * 【对应课程】：集合进阶-10、11、12
 * 【核心价值】：透彻理解 TreeSet、TreeMap 以及 HashMap 链表树化（红黑树）的底层数学模型与旋转算法。
 */
public class D09_TreeDataStructureModule {

    /* =========================================================
     * 1. 自实现二叉查找树（BST - Binary Search Tree）
     *    特性：左子树节点值 < 根节点值 < 右子树节点值
     * ========================================================= */
    static class BSTNode {
        int val;
        BSTNode left;
        BSTNode right;
        BSTNode(int val) { this.val = val; }
    }

    static class SimpleBST {
        BSTNode root;

        public void insert(int val) {
            root = insertRec(root, val);
        }

        private BSTNode insertRec(BSTNode current, int val) {
            if (current == null) {
                return new BSTNode(val);
            }
            if (val < current.val) {
                current.left = insertRec(current.left, val);
            } else if (val > current.val) {
                current.right = insertRec(current.right, val);
            }
            return current;
        }

        // 中序遍历 (LDR)：二叉查找树的中序遍历必然是有序递增序列！
        public void inOrder(BSTNode node, List<Integer> res) {
            if (node == null) return;
            inOrder(node.left, res);
            res.add(node.val);
            inOrder(node.right, res);
        }

        // 计算最大树高（用于展示退化为单向链表的缺陷）
        public int height(BSTNode node) {
            if (node == null) return 0;
            return Math.max(height(node.left), height(node.right)) + 1;
        }
    }

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块九】树形数据结构深度剖析：BST、AVL 旋转与红黑树原理");
        System.out.println("==================================================");

        /* ---------------------------------------------------------
         * 1. 二叉查找树 (BST) 的优势与致命缺陷
         * --------------------------------------------------------- */
        System.out.println("--- 1. 二叉查找树 (BST) 特性与退化缺陷 ---");
        SimpleBST balancedBst = new SimpleBST();
        int[] normalData = {20, 10, 30, 5, 15, 25, 35};
        for (int d : normalData) balancedBst.insert(d);

        List<Integer> inOrderResult = new ArrayList<>();
        balancedBst.inOrder(balancedBst.root, inOrderResult);
        System.out.println("正常插入 7 个元素后中序遍历（天然有序升序）: " + inOrderResult);
        System.out.println("此时树高度: " + balancedBst.height(balancedBst.root) + "（平衡状态，查找时间复杂度 O(log n)）");

        // 致命缺陷：如果依次按升序插入有序数据
        SimpleBST skewedBst = new SimpleBST();
        for (int i = 1; i <= 5; i++) skewedBst.insert(i);
        System.out.println("【致命缺陷】依次插入有序数据 1,2,3,4,5 时：");
        System.out.println("  每个新节点都会挂在上一个节点的右边，树高度直接恶化为: " + skewedBst.height(skewedBst.root));
        System.out.println("  二叉树彻底退化为【单向链表】，查找时间复杂度从 O(log n) 衰减为 O(n)！");

        /* ---------------------------------------------------------
         * 2. 平衡二叉树 (AVL 树) 的旋转机制解密
         * --------------------------------------------------------- */
        System.out.println("\n--- 2. 平衡二叉树 (AVL 树) 与 4 种旋转机制 ---");
        System.out.println("【平衡规则】：任意节点的左右子树高度差（平衡因子 Balance Factor）绝对值 <= 1。");
        System.out.println("【4 种失衡场景与旋转策略】：");
        System.out.println("  ① LL（左左失衡 - 在左孩子的左子树插入导致失衡）：");
        System.out.println("     --> 执行【单次右旋 (Right Rotation)】恢复平衡。");
        System.out.println("  ② RR（右右失衡 - 在右孩子的右子树插入导致失衡）：");
        System.out.println("     --> 执行【单次左旋 (Left Rotation)】恢复平衡。");
        System.out.println("  ③ LR（左右失衡 - 在左孩子的右子树插入导致失衡）：");
        System.out.println("     --> 先对左孩子【局部左旋】，再对根节点【整体右旋】（双旋）。");
        System.out.println("  ④ RL（右左失衡 - 在右孩子的左子树插入导致失衡）：");
        System.out.println("     --> 先对右孩子【局部右旋】，再对根节点【整体左旋】（双旋）。");

        /* ---------------------------------------------------------
         * 3. 红黑树 (Red-Black Tree) 核心规则与平衡维护
         * --------------------------------------------------------- */
        System.out.println("\n--- 3. 红黑树 (Red-Black Tree) 5 大规则与平衡维护 ---");
        System.out.println("【为什么不用 AVL 树？】AVL 树高度差极度严格，插入和删除时会频繁触发旋转，写性能开销大。");
        System.out.println("                   红黑树是一种【弱平衡二叉树】，通过给节点标记红/黑颜色，");
        System.out.println("                   保证从根到叶子的最长路径不超过最短路径的 2 倍，查写性能更均衡！");
        System.out.println("\n【红黑树必须满足的 5 大红黑规则（背诵级重点）】：");
        System.out.println("  规则 1：每个节点要么是【红色】，要么是【黑色】。");
        System.out.println("  规则 2：【根节点必须是黑色】。");
        System.out.println("  规则 3：叶子节点（NIL 空节点）必须是黑色。");
        System.out.println("  规则 4：【不能出现连续两个红色节点】（红色节点的父节点和子节点都必须是黑色）。");
        System.out.println("  规则 5：从任一节点到其所有叶子节点的路径上，【黑色节点数量必须相同】（黑高一致）。");

        System.out.println("\n【红黑树添加节点时的平衡维护流程】：");
        System.out.println("  默认规定：新添加的节点必须先染为【红色】（因为染成黑色必定会破坏规则 5 的黑高）。");
        System.out.println("  - 场景 A：添加的是根节点 -> 直接染为【黑色】（满足规则 2）。");
        System.out.println("  - 场景 B：父节点是黑色 -> 无需任何调整，直接添加成功。");
        System.out.println("  - 场景 C：父节点是红色（违反规则 4，出现红红相连），看【叔叔节点】颜色：");
        System.out.println("      1) 叔叔是红色：【变色处理】 -> 父节点与叔叔节点变为黑色，祖父节点变为红色，再递归检查祖父。");
        System.out.println("      2) 叔叔是黑色/空：【旋转 + 变色】 -> 依据 LL/RR/LR/RL 进行单旋或双旋，并重新染色。");

        System.out.println("\n【在 Java 集合中的实际应用】：");
        System.out.println("  - TreeSet / TreeMap 底层就是红黑树。");
        System.out.println("  - JDK 8+ HashMap 当链表长度 >= 8 且 数组容量 >= 64 时，单向链表会自动树化为红黑树！");
        System.out.println();
    }
}
