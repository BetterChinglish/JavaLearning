package com.betterchinglish.demo20_Exception;

import com.betterchinglish.demo20_Exception.exception.AgeOutOfBoundsException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 模块九：抛出异常机制（throw vs throws 深度辨析与自定义异常实战）
 * ----------------------------------------------------------------------
 * 【对应课程】：异常-09-抛出异常
 * 【throw 与 throws 辨析矩阵】：
 *   | 对比维度 | throw 关键字 | throws 关键字 |
 *   | :--- | :--- | :--- |
 *   | **代码位置** | 位于【方法体内部】 | 位于【方法签名括号后】 |
 *   | **核心动作** | 实际动作：实例化并真正【扔出】一个异常对象 | 预警声明：告知调用者本方法【可能会】抛出某种异常 |
 *   | **后续代码** | throw 之后当前方法立即强行中断，后续代码不执行 | 只是声明，不影响方法体内部代码的正常编写 |
 *   | **跟随后缀** | 后面跟的是【异常对象实体】（如 `throw new XXXException()`）| 后面跟的是【异常类名】（如 `throws IOException, ParseException`）|
 *
 * 【自定义异常实战要领】：
 *   1. 为什么要自定义异常？
 *      - JDK 官方提供的异常类有限，难以精确表达特定业务场景（如：余额不足异常、年龄不合规异常、用户名已被占用异常等）。
 *      - 通过自定义异常类名，可以做到“见名知意”，大幅提升业务代码的可读性和维护性。
 *   2. 继承选择：
 *      - 继承 `RuntimeException`：作为运行时异常，调用方使用更加灵活，无需强制到处写 try-catch；
 *      - 继承 `Exception`：作为编译时异常，强制要求所有调用者必须在编译期做出处理决定。
 */
public class D09_ThrowAndThrowsModule {

    public static void learn() {
        System.out.println("==================================================");
        System.out.println("【模块九】抛出异常机制：throw 与 throws 辨析与实战");
        System.out.println("==================================================");

        /* ---------------------------------------------------------
         * 1. 运行时异常的 throw（无需方法签名显式 throws）
         * --------------------------------------------------------- */
        System.out.println("--- 1. 运行时自定义异常实战 (throw new AgeOutOfBoundsException) ---");
        try {
            System.out.println("尝试设置年龄为: -5 岁");
            setStudentAge(-5);
        } catch (AgeOutOfBoundsException e) {
            System.out.println("--> 调用层捕获到业务打断信号: " + e.getMessage());
        }

        /* ---------------------------------------------------------
         * 2. 编译时受检异常的 throw 与 throws
         * --------------------------------------------------------- */
        System.out.println("\n--- 2. 编译时异常声明与向上传递 (throws) ---");
        // 调用声明了 throws ParseException 的方法，调用者必须主动决断（捕获或继续抛出）
        try {
            Date parsedDate = parseDateString("2026-11-22 23:59:59", "yyyy-MM-dd HH:mm:ss");
            System.out.println("日期解析成功: " + parsedDate);
        } catch (ParseException e) {
            System.out.println("调用者统一处理解析异常: " + e.getMessage());
        }

        /* ---------------------------------------------------------
         * 3. 面试核心总结：异常处理的两大哲学路线
         * --------------------------------------------------------- */
        System.out.println("\n--- 3. 生产开发中异常处理的两大核心哲学 ---");
        System.out.println("  路线 A (内部消化): 若当前代码块拥有解决问题的能力与上下文（如读取默认配置兜底），采用 try-catch 拦截自愈；");
        System.out.println("  路线 B (向外甩锅): 若当前方法仅仅是底层通用工具，根本无权擅自替上层决定业务走向，务必果断 throw 抛出，由顶层统一决策！\n");
    }

    /**
     * 演示运行时异常：使用 throw 在方法内部主动抛出异常
     * 注：因为 AgeOutOfBoundsException 继承自 RuntimeException，故方法签名上的 throws 可以省略！
     */
    public static void setStudentAge(int age) {
        if (age < 0 || age > 150) {
            // throw：方法内部，用于抛出异常结束方法，后续代码不再执行
            throw new AgeOutOfBoundsException("年龄数值非法！传入的年龄: " + age + "，合理范围必须在 0~150 之间");
        }
        System.out.println("年龄设置成功: " + age);
    }

    /**
     * 演示编译时异常：方法内部无法完全消除外界风险，通过 throws 向调用者发出风险预警
     * throws：方法声明处，说明可能会抛出哪些异常，要求调用者必须处理
     */
    public static Date parseDateString(String text, String pattern) throws ParseException {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("待解析的日期文本不能为空！");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        // 此处的 parse 方法声明了 throws ParseException，我们选择直接将其向外抛给更上层调用者
        return sdf.parse(text);
    }
}
