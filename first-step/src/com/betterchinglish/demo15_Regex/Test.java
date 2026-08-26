package com.betterchinglish.demo15_Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式（Regex）教学示例
 * -------------------------------------------------------------
 * 正则用于“匹配 / 查找 / 提取 / 替换”文本，Java 里相关类在 java.util.regex 包下。
 *
 * 两种用法：
 *   1) String 自带的便捷方法（最简单，适合一次性使用）：
 *        str.matches(regex)      判断整体是否匹配
 *        str.split(regex)        按正则切分
 *        str.replaceAll(regex,replacement) / replaceFirst(...)
 *   2) Pattern + Matcher（标准、可复用、功能最强）：
 *        Pattern p = Pattern.compile("正则");
 *        Matcher m = p.matcher(文本);
 *        m.find() / m.matches() / m.group()
 *
 * 重要易错点（务必记住）：
 *   在 Java 字符串里，反斜杠 \ 本身要转义，所以正则的 \d 必须写成 "\\d"，
 *   正则的 \w 写成 "\\w"，字面量反斜杠写成 "\\\\"。这是初学者最常踩的坑。
 */
public class Test {

    public static void main(String[] args) {

        /* =========================================================
         * 0. String 自带的三个便捷方法（最简入门）
         * ========================================================= */

        // 0.1 matches(regex) —— 整体是否匹配（隐含 ^...$，即整串必须完全符合）
        //     入参：String 正则   出参：boolean
        String tel = "13812345678";
        System.out.println("13812345678 是手机号吗: " + tel.matches("1[3-9]\\d{9}")); // true
        System.out.println("123 是手机号吗: " + "123".matches("1[3-9]\\d{9}"));       // false

        // 0.2 split(regex) —— 按正则切分成字符串数组
        //     入参：String 正则（分隔符）   出参：String[]
        String csv = "苹果,香蕉;西瓜|哈密瓜";
        String[] fruits = csv.split("[,;|]"); // 用 [] 表示“其中任意一个”分隔
        System.out.print("切分结果: ");
        for (String f : fruits) System.out.print(f + " "); // 苹果 香蕉 西瓜 哈密瓜
        System.out.println();

        // 0.3 replaceAll / replaceFirst —— 按正则替换（全部 / 第一个）
        //     入参：String 正则, String 替换串   出参：String
        String dirty = "我的密码是123456，验证码是888";
        System.out.println("替换数字为*：" + dirty.replaceAll("\\d", "*"));
        // 输出: 我的密码是******，验证码是***


        /* =========================================================
         * 1. 常用元字符与语法（在注释里讲解，下面用小例子巩固）
         *    .      任意单个字符（除换行）
         *    \\d    数字 [0-9]；\\D 非数字
         *    \\w    单词字符 [a-zA-Z0-9_]；\\W 非单词字符
         *    \\s    空白字符（空格/制表/换行）；\\S 非空白
         *    [abc]  匹配 a 或 b 或 c；[^abc] 除 a/b/c 之外
         *    (xx|yy) 或，匹配 xx 或 yy
         *    ^ 开头  $ 结尾
         *    量词：* 0次或多次；+ 1次或多次；? 0或1次；{n} 恰好n次；{n,} 至少n；{n,m} n到m
         *    贪婪 vs 懒惰：默认贪婪（尽量多匹配），加 ? 变懒惰（尽量少匹配），如 .*? 
         * ========================================================= */

        // 1.1 \\d 与 \\w
        System.out.println("\"a1b2\".matches(\"\\w\\d\\w\\d\") = "
                + "a1b2".matches("\\w\\d\\w\\d")); // true

        // 1.2 [] 字符类 + 量词 {n}
        System.out.println("邮政编码 100000 合法吗: " + "100000".matches("[1-9]\\d{5}")); // true

        // 1.3 贪婪 vs 懒惰（演示区别）
        String html = "<b>粗体</b><i>斜体</i>";
        // 贪婪：.* 会一直吃到最后一个 </>，整段被匹配
        Matcher greedy = Pattern.compile("<b>.*</b>").matcher(html);
        if (greedy.find()) System.out.println("贪婪匹配: " + greedy.group()); // <b>粗体</b><i>斜体</i>
        // 懒惰：.*? 吃到第一个 </> 就停
        Matcher lazy = Pattern.compile("<b>.*?</b>").matcher(html);
        if (lazy.find()) System.out.println("懒惰匹配: " + lazy.group());     // <b>粗体</b>


        /* =========================================================
         * 2. Pattern + Matcher 标准用法（最常用、最强大）
         *    步骤：Pattern.compile(regex) -> matcher(文本) -> find()/matches() -> group()
         *    matcher.find()    : 在文本中“查找下一个”匹配子串（最常用）
         *    matcher.matches() : 整个文本是否“完全”匹配（等价于 String.matches）
         *    matcher.group()   : 拿到当前匹配到的内容
         * ========================================================= */
        Pattern p = Pattern.compile("\\d+");       // 一个或多个数字
        Matcher m = p.matcher("订单号123 金额456 数量7");
        System.out.print("提取到的数字: ");
        while (m.find()) {
            System.out.print(m.group() + " ");      // 123 456 7
        }
        System.out.println();


        /* =========================================================
         * 3. 分组与捕获（用小括号 () 把一部分“圈起来”单独取出来）
         *    group(0) 或 group() : 整个匹配
         *    group(1)            : 第 1 个捕获组（从左往右数第 1 对括号）
         *    group(2)            : 第 2 个捕获组 ……
         * ========================================================= */
        // 从 "姓名:张三,年龄:25" 这类文本里提取姓名和年龄
        Pattern personP = Pattern.compile("姓名:(\\S+),年龄:(\\d+)");
        Matcher personM = personP.matcher("姓名:张三,年龄:25");
        if (personM.find()) {
            System.out.println("整体匹配: " + personM.group(0)); // 姓名:张三,年龄:25
            System.out.println("姓名(组1): " + personM.group(1)); // 张三
            System.out.println("年龄(组2): " + personM.group(2)); // 25
        }


        /* =========================================================
         * 4. 实用示例
         * ========================================================= */

        // 4.1 校验邮箱（简化版正则）
        String emailRegex = "[\\w.]+@[\\w.]+\\.[a-zA-Z]{2,6}";
        System.out.println("test@example.com 合法吗: " + "test@example.com".matches(emailRegex)); // true
        System.out.println("abc@ 合法吗: " + "abc@".matches(emailRegex));                         // false

        // 4.2 手机号脱敏（中间四位打码）
        String phone = "13812345678";
        String masked = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        System.out.println("脱敏后: " + masked); // 138****5678
        // 说明：用两个捕获组分别抓住前3位和后4位，中间4位用 **** 替换

        // 4.3 提取文本里所有邮箱（演示 find 循环 + 分组）
        String text = "联系 alice@a.com 或 bob@b.org.cn 了解详情";
        Pattern mailP = Pattern.compile("([\\w.]+@[\\w.]+\\.[a-zA-Z]{2,6})");
        Matcher mailM = mailP.matcher(text);
        System.out.print("文本中的邮箱: ");
        while (mailM.find()) System.out.print(mailM.group(1) + " "); // alice@a.com bob@b.org.cn
        System.out.println();


        /* =========================================================
         * 5. 编译标志 flags（忽略大小写等）
         *    Pattern.CASE_INSENSITIVE : 忽略大小写
         *    用法：Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
         * ========================================================= */
        Pattern ci = Pattern.compile("java", Pattern.CASE_INSENSITIVE);
        System.out.println("JAVA 能匹配 java(忽略大小写) 吗: " + ci.matcher("JAVA").matches()); // true


        // 补充：常用元字符速查表
        printCheatSheet();
    }

    /* =========================================================
     * 补充教学：常用元字符速查表（打印即可，替代底层原理）
     *   入参：无   出参：无（void），仅打印
     * ========================================================= */
    private static void printCheatSheet() {
        System.out.println("\n========== 正则常用元字符速查 ==========");
        System.out.println("  .        任意单个字符（除换行）");
        System.out.println("  \\d       数字 [0-9]        \\D 非数字");
        System.out.println("  \\w       单词字符 [a-zA-Z0-9_]   \\W 非单词字符");
        System.out.println("  \\s       空白字符         \\S 非空白");
        System.out.println("  [abc]    a 或 b 或 c      [^abc] 除 a/b/c");
        System.out.println("  (a|b)    a 或 b");
        System.out.println("  ^ 开头   $ 结尾");
        System.out.println("  量词:");
        System.out.println("    *   0 次或多次");
        System.out.println("    +   1 次或多次");
        System.out.println("    ?   0 次或 1 次");
        System.out.println("    {n} 恰好 n 次   {n,} 至少 n 次   {n,m} n 到 m 次");
        System.out.println("  贪婪→懒惰: 默认贪婪(多匹配)，量词后加 ? 变懒惰(少匹配)，如 .*?");
        System.out.println("  Java 注意: 源码里 \\d 要写成 \"\\\\d\"（反斜杠需转义）");
    }
}
