# 继承
java的继承是单继承，但可以多层继承


# BigDecimal
`BigDecimal` 在 `java.math` 包下，用于表示**任意精度的十进制数**，专门解决 `double/float` 做精确计算（金钱、利率等）时的精度丢失问题。它是**不可变对象**，所有运算都返回新的 `BigDecimal`。

## 为什么不用 double
`double` 用二进制浮点数表示小数，像 `0.1` 这类十进制小数在二进制里是无限循环小数，只能近似存储。所以：
```java
System.out.println(0.1 + 0.2); // 0.30000000000000004
System.out.println(0.1 + 0.2 == 0.3); // false
```
而 `new BigDecimal("0.1").add(new BigDecimal("0.2"))` 结果是精确的 `0.3`。

## 核心表示模型
一个 `BigDecimal` 的值 = `unscaledValue × 10^(-scale)`
- `unscaledValue`：`BigInteger`，去掉小数点后的整数
- `scale`：`int`，小数点后的位数（负指数）

例如 `12.34` → `unscaledValue=1234, scale=2` → `1234 × 10^-2`。因为底层是整数运算，所以不会像 `double` 那样丢精度。

## 三种构造方式
| 写法 | 说明 |
|------|------|
| `new BigDecimal("10.5")` | **最推荐**，字符串精确构造 |
| `BigDecimal.valueOf(3.14)` | **推荐**，内部先转字符串再构造，安全 |
| `new BigDecimal(0.1)` | **不推荐**，`double` 本身不精确，会带入误差（实际得到 0.1000000000000000055...） |

## 常用运算（不能用 + - * /，要调用方法）
- `add(another)` 加法
- `subtract(another)` 减法
- `multiply(another)` 乘法（积的小数位 = 两数 scale 之和）
- `divide(another)` 除法 —— **最容易踩坑**
  - 除不尽（如 `1 ÷ 3`）且**不指定舍入**会直接抛 `ArithmeticException`
  - 正确写法：`x.divide(y, 2, RoundingMode.HALF_UP)` 指定保留位数 + 舍入模式

## 设置小数位与舍入：setScale + RoundingMode
```java
BigDecimal v = new BigDecimal("2.345");
v.setScale(2, RoundingMode.HALF_UP);    // 2.35 四舍五入（最常用，金额）
v.setScale(2, RoundingMode.HALF_EVEN); // 2.34 银行家舍入（四舍六入五成双）
v.setScale(2, RoundingMode.UP);        // 2.35 远离零进位
v.setScale(2, RoundingMode.DOWN);      // 2.34 趋向零截断
```

## 比较大小用 compareTo，别用 equals
- `compareTo`：只比**数值**，相等返回 0（推荐判断值相等用 `compareTo == 0`）
- `equals`：还会比 **scale**，`new BigDecimal("1.0").equals(new BigDecimal("1.00"))` 是 `false`，但 `compareTo` 认为相等

## 与基本类型互转
`intValue()` / `longValue()` / `doubleValue()` / `toString()`。注意 `doubleValue()` 可能重新引入浮点误差，转字符串优先用 `toString()`。

## 综合例子：金额计算
```java
BigDecimal price = new BigDecimal("19.99");
BigDecimal total = price.multiply(new BigDecimal("3"))
                        .multiply(new BigDecimal("0.85"))
                        .setScale(2, RoundingMode.HALF_UP); // 50.97
```

## 与 BigInteger 对比 & 性能
- `BigInteger`：任意精度**整数**（`scale` 恒为 0）。`BigDecimal` 可理解为“带小数位的 BigInteger”。
- 选型：超大整数用 `BigInteger`；精确金额/小数用 `BigDecimal`。
- 性能：比 `double/float` 硬件浮点运算慢很多，但换来绝对精确，适合金钱、计费等不能出错的场景；海量高性能科学计算仍建议 `double` 或专用数值库。

> 完整可运行示例见 `first-step/src/com/betterchinglish/demo14_BigDecimal/Test.java`。


# 正则表达式
Java 中用正则做**匹配 / 查找 / 提取 / 替换**，相关类在 `java.util.regex` 包下。

## 两种用法
1. **String 便捷方法**（一次性、最简单）：
   - `str.matches(regex)` 整体是否匹配（隐含 `^...$`）
   - `str.split(regex)` 按正则切分
   - `str.replaceAll(regex, rep)` / `replaceFirst(regex, rep)` 替换
2. **Pattern + Matcher**（可复用、功能最强）：
   ```java
   Pattern p = Pattern.compile("正则");
   Matcher m = p.matcher(文本);
   while (m.find()) { System.out.println(m.group()); }
   ```

## 重要易错点：转义
Java 字符串里反斜杠 `\` 本身要转义，所以正则的 `\d` 必须写成 `"\\d"`，`\w` 写成 `"\\w"`，字面量反斜杠写成 `"\\\\"`。

## 常用元字符速查
| 符号 | 含义 |
|------|------|
| `.` | 任意单个字符（除换行） |
| `\d` / `\D` | 数字 / 非数字 |
| `\w` / `\W` | 单词字符 `[a-zA-Z0-9_]` / 非单词字符 |
| `\s` / `\S` | 空白 / 非空白 |
| `[abc]` / `[^abc]` | a或b或c / 除 a,b,c |
| `(a\|b)` | a 或 b |
| `^` / `$` | 开头 / 结尾 |
| `*` `+` `?` | 0+ 次 / 1+ 次 / 0或1次 |
| `{n}` `{n,}` `{n,m}` | 恰好n / 至少n / n到m 次 |

**贪婪 vs 懒惰**：量词默认贪婪（尽量多匹配），在量词后加 `?` 变懒惰（尽量少匹配），如 `.*?`。

## 分组与捕获
用小括号 `()` 圈出要单独提取的部分：
- `group()` / `group(0)`：整个匹配
- `group(1)`：第 1 个捕获组，`group(2)` 第 2 个，依此类推

```java
Pattern p = Pattern.compile("姓名:(\\S+),年龄:(\\d+)");
Matcher m = p.matcher("姓名:张三,年龄:25");
if (m.find()) {
    System.out.println(m.group(1)); // 张三
    System.out.println(m.group(2)); // 25
}
```

## 实用示例
```java
// 校验手机号
"13812345678".matches("1[3-9]\\d{9}");        // true

// 手机号脱敏（中间四位打码）
"13812345678".replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"); // 138****5678

// 提取文本中所有数字
Pattern p = Pattern.compile("\\d+");
Matcher m = p.matcher("订单123 金额456");
while (m.find()) System.out.println(m.group()); // 123  456
```

## matches 与 find 的区别
- `Matcher.matches()`：整个文本**完全**匹配（等同 `String.matches`）
- `Matcher.find()`：在文本中**查找下一个**匹配子串（更常用）

## 编译标志
`Pattern.compile(regex, Pattern.CASE_INSENSITIVE)` 可忽略大小写。

> 完整可运行示例见 `first-step/src/com/betterchinglish/demo15_Regex/Test.java`。

